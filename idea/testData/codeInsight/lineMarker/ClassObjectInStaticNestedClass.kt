trait <lineMarker></lineMarker>TestTrait {
    fun <lineMarker></lineMarker>test()
}

class A {
    class B {
        class object : TestTrait { // TODO: No line marker
            override fun <lineMarker descr="Implements function in 'TestTrait'"></lineMarker>test() {
            }
        }
    }
}