import java.util.Collections

fun <T> foo(a: MutableList<T>): MutableList<MutableList<T>> = Collections.singletonList(a)

fun box(): String {
    val a1: MutableList<out Int> = Collections.singletonList(42)
    val b1: MutableList<out MutableList<out Int>> = foo(a1)
    val c1 = foo(a1)

    val a2: MutableList<in Int> = Collections.singletonList(23)
    val b2: MutableList<out MutableList<in Int>> = foo(a2)
    val c2 = foo(a2)

    return if (b1[0][0] == c1[0][0] && b2[0] == c2[0]) "OK" else "fail"
}