class Inv<T>

fun foo<T>(): Inv<out T> = throw Exception()

// T captures 'in Int'
// lower: Inv<out Int>
// upper: Inv<out Any?>

// T captures 'out Int'
// lower: Inv<Nothing>
// upper: Inv<out Int>
