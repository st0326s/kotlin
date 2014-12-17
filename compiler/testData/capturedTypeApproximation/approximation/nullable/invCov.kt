fun foo<T>(): Inv<Cov<T?>> = throw Exception()

// T captures 'in Int'
// lower: Nothing
// upper: Inv<out Cov<Any?>>

// T captures 'out Int'
// lower: Nothing
// upper: Inv<out Cov<Int>>
