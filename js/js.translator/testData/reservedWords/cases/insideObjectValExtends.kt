package foo

// NOTE THIS FILE IS AUTO-GENERATED by the generateTestDataForReservedWords.kt. DO NOT EDIT!

object TestObject {
    val extends: Int = 0

    fun test() {
        testNotRenamed("extends", { extends })
    }
}

fun box(): String {
    TestObject.test()

    return "OK"
}