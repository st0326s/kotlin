/*
 * Copyright 2010-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.jet.cli.jvm.compiler;

import com.intellij.codeInsight.ContainerProvider;
import com.intellij.codeInsight.ExternalAnnotationsManager;
import com.intellij.codeInsight.runner.JavaMainMethodProvider;
import com.intellij.core.CoreApplicationEnvironment;
import com.intellij.core.CoreJavaFileManager;
import com.intellij.core.JavaCoreApplicationEnvironment;
import com.intellij.core.JavaCoreProjectEnvironment;
import com.intellij.ide.plugins.IdeaPluginDescriptorImpl;
import com.intellij.ide.plugins.PluginManagerCoreProxy;
import com.intellij.lang.java.JavaParserDefinition;
import com.intellij.mock.MockApplication;
import com.intellij.mock.MockProject;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.extensions.ExtensionsArea;
import com.intellij.openapi.fileTypes.ContentBasedFileSubstitutor;
import com.intellij.openapi.fileTypes.FileTypeExtensionPoint;
import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileContextProvider;
import com.intellij.psi.PsiElementFinder;
import com.intellij.psi.PsiManager;
import com.intellij.psi.augment.PsiAugmentProvider;
import com.intellij.psi.compiled.ClassFileDecompilers;
import com.intellij.psi.impl.PsiTreeChangePreprocessor;
import com.intellij.psi.impl.compiled.ClsCustomNavigationPolicy;
import com.intellij.psi.impl.compiled.ClsStubBuilderFactory;
import com.intellij.psi.impl.file.impl.JavaFileManager;
import com.intellij.psi.meta.MetaDataContributor;
import com.intellij.psi.stubs.BinaryFileStubBuilders;
import kotlin.Function1;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;
import org.jetbrains.jet.CompilerModeProvider;
import org.jetbrains.jet.OperationModeProvider;
import org.jetbrains.jet.asJava.JavaElementFinder;
import org.jetbrains.jet.asJava.KotlinLightClassForPackage;
import org.jetbrains.jet.asJava.LightClassGenerationSupport;
import org.jetbrains.jet.cli.common.CLIConfigurationKeys;
import org.jetbrains.jet.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.jet.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.jet.cli.common.messages.MessageCollector;
import org.jetbrains.jet.cli.jvm.JVMConfigurationKeys;
import org.jetbrains.jet.config.CommonConfigurationKeys;
import org.jetbrains.jet.config.CompilerConfiguration;
import org.jetbrains.jet.lang.parsing.JetParserDefinition;
import org.jetbrains.jet.lang.parsing.JetScriptDefinitionProvider;
import org.jetbrains.jet.lang.psi.JetFile;
import org.jetbrains.jet.lang.resolve.CodeAnalyzerInitializer;
import org.jetbrains.jet.lang.resolve.kotlin.KotlinBinaryClassCache;
import org.jetbrains.jet.lang.resolve.kotlin.VirtualFileFinderFactory;
import org.jetbrains.jet.lang.resolve.lazy.declarations.CliDeclarationProviderFactoryService;
import org.jetbrains.jet.lang.resolve.lazy.declarations.DeclarationProviderFactoryService;
import org.jetbrains.jet.plugin.JetFileType;
import org.jetbrains.jet.utils.PathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.jetbrains.jet.cli.common.messages.CompilerMessageSeverity.ERROR;
import static org.jetbrains.jet.cli.common.messages.CompilerMessageSeverity.WARNING;

@SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
public class JetCoreEnvironment {

    private static final Object APPLICATION_LOCK = new Object();
    private static JavaCoreApplicationEnvironment ourApplicationEnvironment;
    private static int ourProjectCount = 0;

    @NotNull
    public static JetCoreEnvironment createForProduction(
            @NotNull Disposable parentDisposable,
            @NotNull CompilerConfiguration configuration,
            @NotNull List<String> configFilePaths
    ) {
        // JPS may run many instances of the compiler in parallel (there's an option for compiling independent modules in parallel in IntelliJ)
        // All projects share the same ApplicationEnvironment, and when the last project is disposed, the ApplicationEnvironment is disposed as well
        Disposer.register(parentDisposable, new Disposable() {
            @Override
            public void dispose() {
                synchronized (APPLICATION_LOCK) {
                    if (--ourProjectCount <= 0) {
                        disposeApplicationEnvironment();
                    }
                }
            }
        });
        JetCoreEnvironment environment =
                new JetCoreEnvironment(parentDisposable, getOrCreateApplicationEnvironmentForProduction(configFilePaths), configuration);

        synchronized (APPLICATION_LOCK) {
            ourProjectCount++;
        }
        return environment;
    }

    @TestOnly
    @NotNull
    public static JetCoreEnvironment createForTests(
            @NotNull Disposable parentDisposable,
            @NotNull CompilerConfiguration configuration,
            @NotNull List<String> extensionConfigs
    ) {
        // Tests are supposed to create a single project and dispose it right after use
        return new JetCoreEnvironment(parentDisposable, createApplicationEnvironment(parentDisposable, extensionConfigs), configuration);
    }

    @NotNull
    private static JavaCoreApplicationEnvironment getOrCreateApplicationEnvironmentForProduction(@NotNull List<String> configFilePaths) {
        synchronized (APPLICATION_LOCK) {
            if (ourApplicationEnvironment != null) return ourApplicationEnvironment;

            Disposable parentDisposable = Disposer.newDisposable();
            ourApplicationEnvironment = createApplicationEnvironment(parentDisposable, configFilePaths);
            ourProjectCount = 0;
            Disposer.register(parentDisposable, new Disposable() {
                @Override
                public void dispose() {
                    synchronized (APPLICATION_LOCK) {
                        ourApplicationEnvironment = null;
                    }
                }
            });
            return ourApplicationEnvironment;
        }
    }

    public static void disposeApplicationEnvironment() {
        synchronized (APPLICATION_LOCK) {
            if (ourApplicationEnvironment == null) return;
            JavaCoreApplicationEnvironment environment = ourApplicationEnvironment;
            ourApplicationEnvironment = null;
            Disposer.dispose(environment.getParentDisposable());
        }
    }

    @NotNull
    private static JavaCoreApplicationEnvironment createApplicationEnvironment(
            @NotNull Disposable parentDisposable,
            @NotNull List<String> configFilePaths
    ) {
        Extensions.cleanRootArea(parentDisposable);
        registerAppExtensionPoints();
        JavaCoreApplicationEnvironment applicationEnvironment = new JavaCoreApplicationEnvironment(parentDisposable);

        for (String config : configFilePaths) {
            registerApplicationExtensionPointsAndExtensionsFrom(config);
        }

        registerApplicationServicesForCLI(applicationEnvironment);
        registerApplicationServices(applicationEnvironment);

        return applicationEnvironment;
    }

    private static void registerAppExtensionPoints() {
        CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), ContentBasedFileSubstitutor.EP_NAME, ContentBasedFileSubstitutor.class);
        CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), BinaryFileStubBuilders.EP_NAME, FileTypeExtensionPoint.class);
        CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), FileContextProvider.EP_NAME, FileContextProvider.class);
        //
        CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), MetaDataContributor.EP_NAME, MetaDataContributor.class);
        CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), ClsStubBuilderFactory.EP_NAME, ClsStubBuilderFactory.class);
        CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), PsiAugmentProvider.EP_NAME, PsiAugmentProvider.class);
        CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), JavaMainMethodProvider.EP_NAME, JavaMainMethodProvider.class);
        //
        CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), ContainerProvider.EP_NAME, ContainerProvider.class);
        CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), ClsCustomNavigationPolicy.EP_NAME, ClsCustomNavigationPolicy.class);
        CoreApplicationEnvironment.registerExtensionPoint(Extensions.getRootArea(), ClassFileDecompilers.EP_NAME,
                                                          ClassFileDecompilers.Decompiler.class);
    }

    private static void registerApplicationExtensionPointsAndExtensionsFrom(String configFilePath) {
        IdeaPluginDescriptorImpl descriptor;
        File jar = PathUtil.getPathUtilJar();
        if (jar.isFile()) {
            descriptor = PluginManagerCoreProxy.loadDescriptorFromJar(jar, configFilePath);
        }
        else {
            // hack for load extensions when compiler run directly from out directory(e.g. in tests)
            File srcDir = jar.getParentFile().getParentFile().getParentFile();
            File pluginDir = new File(srcDir, "idea/src");
            descriptor = PluginManagerCoreProxy.loadDescriptorFromDir(pluginDir, configFilePath);
        }

        assert descriptor != null : "Can not load descriptor from " + configFilePath + " relative to " + jar;

        PluginManagerCoreProxy.registerExtensionPointsAndExtensions(Extensions.getRootArea(), Collections.singletonList(descriptor));
    }

    private static void registerApplicationServicesForCLI(@NotNull JavaCoreApplicationEnvironment applicationEnvironment) {
        // ability to get text from annotations xml files
        applicationEnvironment.registerFileType(PlainTextFileType.INSTANCE, "xml");
        applicationEnvironment.registerParserDefinition(new JavaParserDefinition());
    }

    // made public for Upsource
    public static void registerApplicationServices(@NotNull JavaCoreApplicationEnvironment applicationEnvironment) {
        applicationEnvironment.registerFileType(JetFileType.INSTANCE, "kt");
        applicationEnvironment.registerFileType(JetFileType.INSTANCE, "ktm");
        applicationEnvironment.registerFileType(JetFileType.INSTANCE, JetParserDefinition.STD_SCRIPT_SUFFIX); // should be renamed to kts
        applicationEnvironment.registerParserDefinition(new JetParserDefinition());

        applicationEnvironment.getApplication().registerService(OperationModeProvider.class, new CompilerModeProvider());
        applicationEnvironment.getApplication().registerService(KotlinBinaryClassCache.class, new KotlinBinaryClassCache());
    }

    private final JavaCoreProjectEnvironment projectEnvironment;
    private final List<JetFile> sourceFiles = new ArrayList<JetFile>();
    private final ClassPath classPath = new ClassPath();

    private final CoreExternalAnnotationsManager annotationsManager;

    private final CompilerConfiguration configuration;

    private JetCoreEnvironment(
            @NotNull Disposable parentDisposable,
            @NotNull JavaCoreApplicationEnvironment applicationEnvironment,
            @NotNull CompilerConfiguration configuration
    ) {
        this.configuration = configuration.copy();
        this.configuration.setReadOnly(true);

        projectEnvironment = new JavaCoreProjectEnvironment(parentDisposable, applicationEnvironment){
            @Override
            protected void preregisterServices() {
                registerProjectExtensionPoints(Extensions.getArea(getProject()));
            }
        };

        MockProject project = projectEnvironment.getProject();
        annotationsManager = new CoreExternalAnnotationsManager(project.getComponent(PsiManager.class));
        project.registerService(ExternalAnnotationsManager.class, annotationsManager);
        project.registerService(DeclarationProviderFactoryService.class, new CliDeclarationProviderFactoryService(sourceFiles));

        registerProjectServicesForCLI(projectEnvironment);
        registerProjectServices(projectEnvironment);

        for (File path : configuration.getList(JVMConfigurationKeys.CLASSPATH_KEY)) {
            addToClasspath(path);
        }
        for (File path : configuration.getList(JVMConfigurationKeys.ANNOTATIONS_PATH_KEY)) {
            addExternalAnnotationsRoot(path);
        }
        sourceFiles.addAll(
                CompileEnvironmentUtil
                        .getJetFiles(getProject(), configuration.getList(CommonConfigurationKeys.SOURCE_ROOTS_KEY),
                                     new Function1<String, Unit>() {
                                         @Override
                                         public Unit invoke(String s) {
                                             report(ERROR, s);
                                             return Unit.INSTANCE$;
                                         }
                                     }));
        JetScriptDefinitionProvider.getInstance(project).addScriptDefinitions(
                configuration.getList(CommonConfigurationKeys.SCRIPT_DEFINITIONS_KEY));

        project.registerService(VirtualFileFinderFactory.class, new CliVirtualFileFinderFactory(classPath));
    }

    private static void registerProjectExtensionPoints(ExtensionsArea area) {
        CoreApplicationEnvironment.registerExtensionPoint(area, PsiTreeChangePreprocessor.EP_NAME, PsiTreeChangePreprocessor.class);
        CoreApplicationEnvironment.registerExtensionPoint(area, PsiElementFinder.EP_NAME, PsiElementFinder.class);
    }

    // made public for Upsource
    public static void registerProjectServices(@NotNull JavaCoreProjectEnvironment projectEnvironment) {
        MockProject project = projectEnvironment.getProject();
        project.registerService(JetScriptDefinitionProvider.class, new JetScriptDefinitionProvider());

        project.registerService(KotlinLightClassForPackage.FileStubCache.class, new KotlinLightClassForPackage.FileStubCache(project));
    }

    private static void registerProjectServicesForCLI(@NotNull JavaCoreProjectEnvironment projectEnvironment) {
        MockProject project = projectEnvironment.getProject();
        project.registerService(CoreJavaFileManager.class, (CoreJavaFileManager) ServiceManager.getService(project, JavaFileManager.class));
        CliLightClassGenerationSupport cliLightClassGenerationSupport = new CliLightClassGenerationSupport();
        project.registerService(LightClassGenerationSupport.class, cliLightClassGenerationSupport);
        project.registerService(CliLightClassGenerationSupport.class, cliLightClassGenerationSupport);
        project.registerService(CodeAnalyzerInitializer.class, cliLightClassGenerationSupport);
        Extensions.getArea(project)
                .getExtensionPoint(PsiElementFinder.EP_NAME)
                .registerExtension(new JavaElementFinder(project, cliLightClassGenerationSupport));
    }

    @NotNull
    public CompilerConfiguration getConfiguration() {
        return configuration;
    }

    @NotNull
    private CoreApplicationEnvironment getMyApplicationEnvironment() {
        return projectEnvironment.getEnvironment();
    }

    @NotNull
    public MockApplication getApplication() {
        return getMyApplicationEnvironment().getApplication();
    }

    @NotNull
    public Project getProject() {
        return projectEnvironment.getProject();
    }

    private void addExternalAnnotationsRoot(File path) {
        if (!path.exists()) {
            report(WARNING, "Annotations path entry points to a non-existent location: " + path);
            return;
        }
        annotationsManager.addExternalAnnotationsRoot(PathUtil.jarFileOrDirectoryToVirtualFile(path));
    }

    private void addToClasspath(File path) {
        if (path.isFile()) {
            VirtualFile jarFile = getMyApplicationEnvironment().getJarFileSystem().findFileByPath(path + "!/");
            if (jarFile == null) {
                report(WARNING, "Classpath entry points to a file that is not a JAR archive: " + path);
                return;
            }
            projectEnvironment.addJarToClassPath(path);
            classPath.add(jarFile);
        }
        else {
            VirtualFile root = getMyApplicationEnvironment().getLocalFileSystem().findFileByPath(path.getAbsolutePath());
            if (root == null) {
                report(WARNING, "Classpath entry points to a non-existent location: " + path);
                return;
            }
            projectEnvironment.addSourcesToClasspath(root);
            classPath.add(root);
        }
    }

    @NotNull
    public List<JetFile> getSourceFiles() {
        return sourceFiles;
    }

    private void report(@NotNull CompilerMessageSeverity severity, @NotNull String message) {
        MessageCollector messageCollector = configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        if (messageCollector != null) {
            messageCollector.report(severity, message, CompilerMessageLocation.NO_LOCATION);
        }
        else {
            throw new CompileEnvironmentException(message);
        }
    }
}
