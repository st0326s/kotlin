/*
 * Copyright 2010-2014 JetBrains s.r.o.
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

package kotlin.reflect.jvm.internal

fun <T> kClass(jClass: Class<T>): KClassImpl<T> =
        KClassImpl(jClass)

fun <T> kClassFromKotlin(jClass: Class<T>): KClassImpl<T> =
        KClassImpl(jClass, true)

fun kPackage(jClass: Class<*>): KPackageImpl =
        KPackageImpl(jClass)

fun topLevelVariable(name: String, owner: KPackageImpl): KTopLevelVariableImpl<Any?> =
        KTopLevelVariableImpl(name, owner)

fun mutableTopLevelVariable(name: String, owner: KPackageImpl): KMutableTopLevelVariableImpl<Any?> =
        KMutableTopLevelVariableImpl(name, owner)

fun <T> topLevelExtensionProperty(name: String, owner: KPackageImpl, receiver: Class<T>): KTopLevelExtensionPropertyImpl<T, Any?> =
        KTopLevelExtensionPropertyImpl(name, owner, receiver)

fun <T> mutableTopLevelExtensionProperty(name: String, owner: KPackageImpl, receiver: Class<T>): KMutableTopLevelExtensionPropertyImpl<T, Any?> =
        KMutableTopLevelExtensionPropertyImpl(name, owner, receiver)
