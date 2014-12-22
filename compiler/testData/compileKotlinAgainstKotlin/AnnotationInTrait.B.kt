class C : a.Tr

fun main(args: Array<String>) {
    val method = javaClass<C>().getDeclaredMethod("foo")
    val declaredAnnotations = method.getDeclaredAnnotations()
    if (declaredAnnotations.size() != 1) {
        throw AssertionError(declaredAnnotations.joinToString("\n"))
    }
}