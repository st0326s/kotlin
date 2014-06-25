// Auto-generated by org.jetbrains.jet.generators.tests.GenerateRangesCodegenTestData. DO NOT EDIT!
import java.util.ArrayList
import java.lang as j

fun box(): String {
    val list1 = ArrayList<Int>()
    val range1 = (8 downTo 3 step 2).reversed()
    for (i in range1) {
        list1.add(i)
        if (list1.size() > 23) break
    }
    if (list1 != listOf<Int>(3, 5, 7)) {
        return "Wrong elements for (8 downTo 3 step 2).reversed(): $list1"
    }

    val list2 = ArrayList<Byte>()
    val range2 = (8.toByte() downTo 3.toByte() step 2).reversed()
    for (i in range2) {
        list2.add(i)
        if (list2.size() > 23) break
    }
    if (list2 != listOf<Byte>(3, 5, 7)) {
        return "Wrong elements for (8.toByte() downTo 3.toByte() step 2).reversed(): $list2"
    }

    val list3 = ArrayList<Short>()
    val range3 = (8.toShort() downTo 3.toShort() step 2).reversed()
    for (i in range3) {
        list3.add(i)
        if (list3.size() > 23) break
    }
    if (list3 != listOf<Short>(3, 5, 7)) {
        return "Wrong elements for (8.toShort() downTo 3.toShort() step 2).reversed(): $list3"
    }

    val list4 = ArrayList<Long>()
    val range4 = (8.toLong() downTo 3.toLong() step 2.toLong()).reversed()
    for (i in range4) {
        list4.add(i)
        if (list4.size() > 23) break
    }
    if (list4 != listOf<Long>(3, 5, 7)) {
        return "Wrong elements for (8.toLong() downTo 3.toLong() step 2.toLong()).reversed(): $list4"
    }

    val list5 = ArrayList<Char>()
    val range5 = ('d' downTo 'a' step 2).reversed()
    for (i in range5) {
        list5.add(i)
        if (list5.size() > 23) break
    }
    if (list5 != listOf<Char>('a', 'c')) {
        return "Wrong elements for ('d' downTo 'a' step 2).reversed(): $list5"
    }

    val list6 = ArrayList<Double>()
    val range6 = (5.8 downTo 4.0 step 0.5).reversed()
    for (i in range6) {
        list6.add(i)
        if (list6.size() > 23) break
    }
    if (list6 != listOf<Double>(4.0, 4.5, 5.0, 5.5)) {
        return "Wrong elements for (5.8 downTo 4.0 step 0.5).reversed(): $list6"
    }

    val list7 = ArrayList<Float>()
    val range7 = (5.8.toFloat() downTo 4.0.toFloat() step 0.5.toFloat()).reversed()
    for (i in range7) {
        list7.add(i)
        if (list7.size() > 23) break
    }
    if (list7 != listOf<Float>(4.0.toFloat(), 4.5.toFloat(), 5.0.toFloat(), 5.5.toFloat())) {
        return "Wrong elements for (5.8.toFloat() downTo 4.0.toFloat() step 0.5.toFloat()).reversed(): $list7"
    }

    return "OK"
}