open class A {
    open fun foo(): Int {
        return 2
    }
}

trait T {
    open fun foo(): Int {
        return 3
    }
}

object O: A(), T {
    override fun foo(): Int {
        return super<A>.foo() + super<T>.foo()
    }
}

fun box() : String {
  return if (O.foo() == 5) "OK" else "fail"
}
