fun foo<T>(): Con<T?> = throw Exception()

// T captures 'in Int'
// lower: Con<Any?>
// upper: Con<Int>

// T captures 'out Int'
// lower: Con<Int>
// upper: Con<Nothing>
