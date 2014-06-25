package kotlin

//
// NOTE THIS FILE IS AUTO-GENERATED by the GenerateStandardLib.kt
// See: https://github.com/JetBrains/kotlin/tree/master/libraries/stdlib
//

import java.util.*

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <T, R> Array<out T>.flatMap(transform: (T) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <R> BooleanArray.flatMap(transform: (Boolean) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <R> ByteArray.flatMap(transform: (Byte) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <R> CharArray.flatMap(transform: (Char) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <R> DoubleArray.flatMap(transform: (Double) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <R> FloatArray.flatMap(transform: (Float) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <R> IntArray.flatMap(transform: (Int) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <R> LongArray.flatMap(transform: (Long) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <R> ShortArray.flatMap(transform: (Short) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <T, R> Iterable<T>.flatMap(transform: (T) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <K, V, R> Map<K, V>.flatMap(transform: (Map.Entry<K, V>) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single list of all elements yielded from results of *transform* function being invoked on each element of original collection
 */
public inline fun <R> String.flatMap(transform: (Char) -> Iterable<R>): List<R> {
    return flatMapTo(ArrayList<R>(), transform)
}

/**
 * Returns a single stream of all elements streamed from results of *transform* function being invoked on each element of original stream
 */
public fun <T, R> Stream<T>.flatMap(transform: (T) -> Stream<R>): Stream<R> {
    return FlatteningStream(this, transform)
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <T, R, C : MutableCollection<in R>> Array<out T>.flatMapTo(destination: C, transform: (T) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> BooleanArray.flatMapTo(destination: C, transform: (Boolean) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> ByteArray.flatMapTo(destination: C, transform: (Byte) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> CharArray.flatMapTo(destination: C, transform: (Char) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> DoubleArray.flatMapTo(destination: C, transform: (Double) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> FloatArray.flatMapTo(destination: C, transform: (Float) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> IntArray.flatMapTo(destination: C, transform: (Int) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> LongArray.flatMapTo(destination: C, transform: (Long) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> ShortArray.flatMapTo(destination: C, transform: (Short) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.flatMapTo(destination: C, transform: (T) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <K, V, R, C : MutableCollection<in R>> Map<K, V>.flatMapTo(destination: C, transform: (Map.Entry<K, V>) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original collection, to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> String.flatMapTo(destination: C, transform: (Char) -> Iterable<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Appends all elements yielded from results of *transform* function being invoked on each element of original stream, to the given *destination*
 */
public inline fun <T, R, C : MutableCollection<in R>> Stream<T>.flatMapTo(destination: C, transform: (T) -> Stream<R>): C {
    for (element in this) {
        val list = transform(element)
        destination.addAll(list)
    }
    return destination
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <T, K> Array<out T>.groupBy(toKey: (T) -> K): Map<K, List<T>> {
    return groupByTo(HashMap<K, MutableList<T>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <K> BooleanArray.groupBy(toKey: (Boolean) -> K): Map<K, List<Boolean>> {
    return groupByTo(HashMap<K, MutableList<Boolean>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <K> ByteArray.groupBy(toKey: (Byte) -> K): Map<K, List<Byte>> {
    return groupByTo(HashMap<K, MutableList<Byte>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <K> CharArray.groupBy(toKey: (Char) -> K): Map<K, List<Char>> {
    return groupByTo(HashMap<K, MutableList<Char>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <K> DoubleArray.groupBy(toKey: (Double) -> K): Map<K, List<Double>> {
    return groupByTo(HashMap<K, MutableList<Double>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <K> FloatArray.groupBy(toKey: (Float) -> K): Map<K, List<Float>> {
    return groupByTo(HashMap<K, MutableList<Float>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <K> IntArray.groupBy(toKey: (Int) -> K): Map<K, List<Int>> {
    return groupByTo(HashMap<K, MutableList<Int>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <K> LongArray.groupBy(toKey: (Long) -> K): Map<K, List<Long>> {
    return groupByTo(HashMap<K, MutableList<Long>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <K> ShortArray.groupBy(toKey: (Short) -> K): Map<K, List<Short>> {
    return groupByTo(HashMap<K, MutableList<Short>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <T, K> Iterable<T>.groupBy(toKey: (T) -> K): Map<K, List<T>> {
    return groupByTo(HashMap<K, MutableList<T>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <V, K> Map<K, V>.groupBy(toKey: (Map.Entry<K, V>) -> K): Map<K, List<Map.Entry<K, V>>> {
    return groupByTo(HashMap<K, MutableList<Map.Entry<K, V>>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <T, K> Stream<T>.groupBy(toKey: (T) -> K): Map<K, List<T>> {
    return groupByTo(HashMap<K, MutableList<T>>(), toKey)
}

/**
 * Returns a map of the elements in original collection grouped by the result of given *toKey* function
 */
public inline fun <K> String.groupBy(toKey: (Char) -> K): Map<K, List<Char>> {
    return groupByTo(HashMap<K, MutableList<Char>>(), toKey)
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <T, K> Array<out T>.groupByTo(map: MutableMap<K, MutableList<T>>, toKey: (T) -> K): Map<K, MutableList<T>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<T>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <K> BooleanArray.groupByTo(map: MutableMap<K, MutableList<Boolean>>, toKey: (Boolean) -> K): Map<K, MutableList<Boolean>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<Boolean>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <K> ByteArray.groupByTo(map: MutableMap<K, MutableList<Byte>>, toKey: (Byte) -> K): Map<K, MutableList<Byte>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<Byte>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <K> CharArray.groupByTo(map: MutableMap<K, MutableList<Char>>, toKey: (Char) -> K): Map<K, MutableList<Char>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<Char>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <K> DoubleArray.groupByTo(map: MutableMap<K, MutableList<Double>>, toKey: (Double) -> K): Map<K, MutableList<Double>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<Double>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <K> FloatArray.groupByTo(map: MutableMap<K, MutableList<Float>>, toKey: (Float) -> K): Map<K, MutableList<Float>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<Float>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <K> IntArray.groupByTo(map: MutableMap<K, MutableList<Int>>, toKey: (Int) -> K): Map<K, MutableList<Int>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<Int>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <K> LongArray.groupByTo(map: MutableMap<K, MutableList<Long>>, toKey: (Long) -> K): Map<K, MutableList<Long>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<Long>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <K> ShortArray.groupByTo(map: MutableMap<K, MutableList<Short>>, toKey: (Short) -> K): Map<K, MutableList<Short>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<Short>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <T, K> Iterable<T>.groupByTo(map: MutableMap<K, MutableList<T>>, toKey: (T) -> K): Map<K, MutableList<T>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<T>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <V, K> Map<K, V>.groupByTo(map: MutableMap<K, MutableList<Map.Entry<K, V>>>, toKey: (Map.Entry<K, V>) -> K): Map<K, MutableList<Map.Entry<K, V>>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<Map.Entry<K, V>>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <T, K> Stream<T>.groupByTo(map: MutableMap<K, MutableList<T>>, toKey: (T) -> K): Map<K, MutableList<T>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<T>() }
        list.add(element)
    }
    return map
}

/**
 * Appends elements from original collection grouped by the result of given *toKey* function to the given *map*
 */
public inline fun <K> String.groupByTo(map: MutableMap<K, MutableList<Char>>, toKey: (Char) -> K): Map<K, MutableList<Char>> {
    for (element in this) {
        val key = toKey(element)
        val list = map.getOrPut(key) { ArrayList<Char>() }
        list.add(element)
    }
    return map
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <T, R> Array<out T>.map(transform: (T) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <R> BooleanArray.map(transform: (Boolean) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <R> ByteArray.map(transform: (Byte) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <R> CharArray.map(transform: (Char) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <R> DoubleArray.map(transform: (Double) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <R> FloatArray.map(transform: (Float) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <R> IntArray.map(transform: (Int) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <R> LongArray.map(transform: (Long) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <R> ShortArray.map(transform: (Short) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <K, V, R> Map<K, V>.map(transform: (Map.Entry<K, V>) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a stream containing the results of applying the given *transform* function to each element of the original stream
 */
public fun <T, R> Stream<T>.map(transform: (T) -> R): Stream<R> {
    return TransformingStream(this, transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each element of the original collection
 */
public inline fun <R> String.map(transform: (Char) -> R): List<R> {
    return mapTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each non-null element of the original collection
 */
public inline fun <T : Any, R> Array<T?>.mapNotNull(transform: (T) -> R): List<R> {
    return mapNotNullTo(ArrayList<R>(), transform)
}

/**
 * Returns a list containing the results of applying the given *transform* function to each non-null element of the original collection
 */
public inline fun <T : Any, R> Iterable<T?>.mapNotNull(transform: (T) -> R): List<R> {
    return mapNotNullTo(ArrayList<R>(), transform)
}

/**
 * Returns a stream containing the results of applying the given *transform* function to each non-null element of the original stream
 */
public fun <T : Any, R> Stream<T?>.mapNotNull(transform: (T) -> R): Stream<R> {
    return TransformingStream(FilteringStream(this, false, { it == null }) as Stream<T>, transform)
}

/**
 * Appends transformed non-null elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <T : Any, R, C : MutableCollection<in R>> Array<T?>.mapNotNullTo(destination: C, transform: (T) -> R): C {
    for (element in this) {
        if (element != null) {
            destination.add(transform(element))
        }
    }
    return destination
}

/**
 * Appends transformed non-null elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <T : Any, R, C : MutableCollection<in R>> Iterable<T?>.mapNotNullTo(destination: C, transform: (T) -> R): C {
    for (element in this) {
        if (element != null) {
            destination.add(transform(element))
        }
    }
    return destination
}

/**
 * Appends transformed non-null elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <T : Any, R, C : MutableCollection<in R>> Stream<T?>.mapNotNullTo(destination: C, transform: (T) -> R): C {
    for (element in this) {
        if (element != null) {
            destination.add(transform(element))
        }
    }
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <T, R, C : MutableCollection<in R>> Array<out T>.mapTo(destination: C, transform: (T) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> BooleanArray.mapTo(destination: C, transform: (Boolean) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> ByteArray.mapTo(destination: C, transform: (Byte) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> CharArray.mapTo(destination: C, transform: (Char) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> DoubleArray.mapTo(destination: C, transform: (Double) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> FloatArray.mapTo(destination: C, transform: (Float) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> IntArray.mapTo(destination: C, transform: (Int) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> LongArray.mapTo(destination: C, transform: (Long) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> ShortArray.mapTo(destination: C, transform: (Short) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.mapTo(destination: C, transform: (T) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <K, V, R, C : MutableCollection<in R>> Map<K, V>.mapTo(destination: C, transform: (Map.Entry<K, V>) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <T, R, C : MutableCollection<in R>> Stream<T>.mapTo(destination: C, transform: (T) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Appends transformed elements of original collection using the given *transform* function
 * to the given *destination*
 */
public inline fun <R, C : MutableCollection<in R>> String.mapTo(destination: C, transform: (Char) -> R): C {
    for (item in this)
        destination.add(transform(item))
    return destination
}

/**
 * Returns a list containing pairs of each element of the original collection and their index
 */
public fun <T> Array<out T>.withIndices(): List<Pair<Int, T>> {
    var index = 0
    return mapTo(ArrayList<Pair<Int, T>>(), { index++ to it })
}

/**
 * Returns a list containing pairs of each element of the original collection and their index
 */
public fun BooleanArray.withIndices(): List<Pair<Int, Boolean>> {
    var index = 0
    return mapTo(ArrayList<Pair<Int, Boolean>>(), { index++ to it })
}

/**
 * Returns a list containing pairs of each element of the original collection and their index
 */
public fun ByteArray.withIndices(): List<Pair<Int, Byte>> {
    var index = 0
    return mapTo(ArrayList<Pair<Int, Byte>>(), { index++ to it })
}

/**
 * Returns a list containing pairs of each element of the original collection and their index
 */
public fun CharArray.withIndices(): List<Pair<Int, Char>> {
    var index = 0
    return mapTo(ArrayList<Pair<Int, Char>>(), { index++ to it })
}

/**
 * Returns a list containing pairs of each element of the original collection and their index
 */
public fun DoubleArray.withIndices(): List<Pair<Int, Double>> {
    var index = 0
    return mapTo(ArrayList<Pair<Int, Double>>(), { index++ to it })
}

/**
 * Returns a list containing pairs of each element of the original collection and their index
 */
public fun FloatArray.withIndices(): List<Pair<Int, Float>> {
    var index = 0
    return mapTo(ArrayList<Pair<Int, Float>>(), { index++ to it })
}

/**
 * Returns a list containing pairs of each element of the original collection and their index
 */
public fun IntArray.withIndices(): List<Pair<Int, Int>> {
    var index = 0
    return mapTo(ArrayList<Pair<Int, Int>>(), { index++ to it })
}

/**
 * Returns a list containing pairs of each element of the original collection and their index
 */
public fun LongArray.withIndices(): List<Pair<Int, Long>> {
    var index = 0
    return mapTo(ArrayList<Pair<Int, Long>>(), { index++ to it })
}

/**
 * Returns a list containing pairs of each element of the original collection and their index
 */
public fun ShortArray.withIndices(): List<Pair<Int, Short>> {
    var index = 0
    return mapTo(ArrayList<Pair<Int, Short>>(), { index++ to it })
}

/**
 * Returns a list containing pairs of each element of the original collection and their index
 */
public fun <T> Iterable<T>.withIndices(): List<Pair<Int, T>> {
    var index = 0
    return mapTo(ArrayList<Pair<Int, T>>(), { index++ to it })
}

/**
 * Returns a stream containing pairs of each element of the original collection and their index
 */
public fun <T> Stream<T>.withIndices(): Stream<Pair<Int, T>> {
    var index = 0
    return TransformingStream(this, { index++ to it })
}

/**
 * Returns a list containing pairs of each element of the original collection and their index
 */
public fun String.withIndices(): List<Pair<Int, Char>> {
    var index = 0
    return mapTo(ArrayList<Pair<Int, Char>>(), { index++ to it })
}
