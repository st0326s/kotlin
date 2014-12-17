class Inv<T>

fun foo<T>(): Inv<Inv<Inv<T>>> = throw Exception()

// T captures 'in Int'
// lower: Nothing
// upper: Inv<out Inv<out Inv<in Int>>>

// T captures 'out Int'
// lower: Nothing
// upper: Inv<out Inv<out Inv<out Int>>>
