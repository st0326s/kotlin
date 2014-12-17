fun foo<T>(): Cov<Con<T?>> = throw Exception()

// T captures 'in Int'
// lower: Cov<Con<Any?>>
// upper: Cov<Con<Int>>

// T captures 'out Int'
// lower: Cov<Con<Int>>
// upper: Cov<Con<Nothing>>
