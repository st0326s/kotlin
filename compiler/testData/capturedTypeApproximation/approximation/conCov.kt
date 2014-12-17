fun foo<T>(): Con<Cov<T>> = throw Exception()

// T captures 'in Int'
// lower: Con<Cov<Any?>>
// upper: Con<Cov<Int>>

// T captures 'out Int'
// lower: Con<Cov<Int>>
// upper: Con<Cov<Nothing>>
