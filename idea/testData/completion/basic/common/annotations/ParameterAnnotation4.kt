val v = 1

fun foo(volatile <caret>) { }

// INVOCATION_COUNT: 1
// EXIST: inlineOptions
// ABSENT: String
// ABSENT: v
