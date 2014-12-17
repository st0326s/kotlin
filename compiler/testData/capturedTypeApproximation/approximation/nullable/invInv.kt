fun foo<T>(): Inv<Inv<T?>> = throw Exception()

// T captures 'in Int'
// lower: Nothing
// upper: Inv<out Inv<in Int>>

// T captures 'out Int'
// lower: Nothing
// upper: Inv<out Inv<out Int>>
