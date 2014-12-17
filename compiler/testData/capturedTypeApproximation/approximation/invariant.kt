class Inv<T>

fun foo<T>(): Inv<T> = throw Exception()

// T captures 'in Int'
// lower: Nothing
// upper: Inv<in Int>

// T captures 'out Int'
// lower: Nothing
// upper: Inv<out Int>
