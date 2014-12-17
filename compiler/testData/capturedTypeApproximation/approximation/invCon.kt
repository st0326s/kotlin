fun foo<T>(): Inv<Con<T>> = throw Exception()

// T captures 'in Int'
// lower: Nothing
// upper: Inv<out Con<Int>>

// T captures 'out Int'
// lower: Nothing
// upper: Inv<out Con<Nothing>>
