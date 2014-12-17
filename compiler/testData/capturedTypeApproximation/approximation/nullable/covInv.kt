fun foo<T>(): Cov<Inv<T?>> = throw Exception()

// T captures 'in Int'
// lower: Cov<Nothing>
// upper: Cov<Inv<in Int>>

// T captures 'out Int'
// lower: Cov<Nothing>
// upper: Cov<Inv<out Int>>
