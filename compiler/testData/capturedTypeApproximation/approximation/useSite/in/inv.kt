class Inv<T>

fun foo<T>(): Inv<in T> = throw Exception()

// T captures 'in Int'
// lower: Inv<Any?>
// upper: Inv<in Int>

// T captures 'out Int'
// lower: Inv<in Int>
// upper: Inv<out Any?>
