package foo

// NOTE THIS FILE IS AUTO-GENERATED by the generateTestDataForReservedWords.kt. DO NOT EDIT!

enum class protected { foo }

fun box(): String {
    testNotRenamed("protected", { protected.foo })

    return "OK"
}