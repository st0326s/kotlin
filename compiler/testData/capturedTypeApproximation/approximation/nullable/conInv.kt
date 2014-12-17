fun foo<T>(): Con<Inv<T?>> = throw Exception()

// T captures 'in Int'
// lower: Con<Inv<in Int>>
// upper: Con<Nothing>

// T captures 'out Int'
// lower: Con<Inv<out Int>>
// upper: Con<Nothing>
