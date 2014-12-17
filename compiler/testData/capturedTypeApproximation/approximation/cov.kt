fun foo<T>(): Cov<T> = throw Exception()

// T captures 'in Int'
// lower: Cov<Int>
// upper: Cov<Any?>

// T captures 'out Int'
// lower: Cov<Nothing>
// upper: Cov<Int>
