fun foo<T>(): Con<Con<T?>> = throw Exception()

// T captures 'in Int'
// lower: Con<Con<Int>>
// upper: Con<Con<Any?>>

// T captures 'out Int'
// lower: Con<Con<Nothing>>
// upper: Con<Con<Int>>
