package foo

// NOTE THIS FILE IS AUTO-GENERATED by the generateTestDataForReservedWords.kt. DO NOT EDIT!

enum class Foo {
    BAR
    val protected: Int = 0

    fun test() {
        testNotRenamed("protected", { protected })
    }
}

fun box(): String {
    Foo.BAR.test()

    return "OK"
}