fun foo<T>(): Cov<Cov<T?>> = throw Exception()

// T captures 'in Int'
// lower: Cov<Cov<Int>>
// upper: Cov<Cov<Any?>>

// T captures 'out Int'
// lower: Cov<Cov<Nothing>>
// upper: Cov<Cov<Int>>
