val v = 1

fun foo(p: String, <caret>) { }

// INVOCATION_COUNT: 1
// EXIST: inlineOptions
// ABSENT: String
// ABSENT: v
