class Contr<in T>

fun foo<T>(): Contr<T> = throw Exception()

// T captures 'in Int'
// lower: Contr<Any?>
// upper: Contr<Int>

// T captures 'out Int'
// lower: Contr<Int>
// upper: Contr<Nothing>
