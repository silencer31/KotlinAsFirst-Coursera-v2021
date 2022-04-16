@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.sqr
import kotlin.math.*

// Урок 3: циклы
// Максимальное количество баллов = 9
// Рекомендуемое количество баллов = 7
// Вместе с предыдущими уроками = 16/21

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая (2 балла)
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    val nabs = abs(n)
    if (nabs < 10) return 1

    var digNumber: Int = 1
    var compareDigit: Long = 10

    while (compareDigit <= nabs.toLong()) {
        compareDigit *= 10
        digNumber++
    }

    return digNumber
}

/**
 * Простая (2 балла)
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if ((n == 1) || (n == 2)) return 1

    var sum: Int = 0
    var theFirst: Int = 1
    var theSecond: Int = 1

    for (j in 3..n) {

        sum = theFirst + theSecond
        theFirst = theSecond
        theSecond = sum
    }

    return sum
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var minDiv: Int = 1
    var rest: Int = 0

    do {
        minDiv++
        rest = n % minDiv
    } while (rest != 0)

    return minDiv
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var maxDiv: Int = n
    var rest: Int = 0

    do {
        maxDiv--
        rest = n % maxDiv
    } while (rest != 0)

    return maxDiv
}

/**
 * Простая (2 балла)
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var nextNumber: Int = x
    var sum: Int = 0

    while (nextNumber != 1) {
        if (nextNumber % 2 == 0) { // Четные
            nextNumber /= 2
        }
        else { // Нечетные
            nextNumber = 3 * nextNumber + 1
        }

        sum++
    }

    return sum
}

/**
 * Средняя (3 балла)
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var k: Int = max(m, n) - 1
    var rest: Int = 0

    do {
        k++
        rest = (k % m) + (k % n)
    } while(rest != 0)

    return k
}

/**
 * Средняя (3 балла)
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    val minVal = min(m, n)
    val maxVal = max(m, n)

    if (minVal == 1) return false

    if (maxVal % minVal == 0) return false

    for (i in minVal downTo 2) {
        if (minVal % i == 0) {
            if (maxVal % i == 0) {
                return false
            }
        }
    }

    return true
}

/**
 * Средняя (3 балла)
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    if (n < 10) return n

    var mainValue: Int = n
    var tailValue: Int = 0

    var revertValue: Int = 0

    while (mainValue != 0) {
        tailValue = mainValue % 10
        mainValue /= 10

        if (mainValue < 10) {
            revertValue = (revertValue * 10) + tailValue
            revertValue = (revertValue * 10) + mainValue
            break
        }
        else {
            revertValue = (revertValue * 10) + tailValue
         }
    }

    return revertValue
}

/**
 * Средняя (3 балла)
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    if (n < 10) return true

    var revValue = revert(n)
    var digitsNumber: Int = 1
    var baseValue: Int = revValue

    while (true) {
        baseValue /= 10
        if (baseValue == 0) break
        digitsNumber++
    }

    baseValue = n

    for (i in 0 until (digitsNumber / 2)) {
        if ((baseValue % 10) != (revValue % 10)) return false
        baseValue /= 10
        revValue /= 10
    }

    return true
}

/**
 * Средняя (3 балла)
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    if (n < 10) return false

    var baseValue: Int = n / 10
    val tailVal: Int = n % 10

    while (true) {
        if ((baseValue % 10) != tailVal) {
            return true
        }
        baseValue /= 10

        if (baseValue == 0) break
    }

    return false
}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double = TODO()

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double = TODO()

/**
 * Сложная (4 балла)
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var counted: Int = 0 // Кол-во чисел, которое посчитано.
    var revertSqr: Int = 0 //
    var sqrVal: Int = 0
    var answer: Int = 0
    var zerosNumber: Int = 0

    for (i in 1 until (n + 1)) {
        sqrVal = sqr(i)
        while (true) {
            if ( (sqrVal % 10) == 0) {
                zerosNumber++
            }
            else {
                break
            }

            sqrVal /= 10
            if (sqrVal < 10) break
        }

        revertSqr = revert(sqr(i))

        while (true) {
            if (zerosNumber > 0) {
                answer = 0
                zerosNumber--
                counted++
                if (counted == n ) return answer
            }
            else {
                answer = revertSqr % 10
                counted++
                if (counted == n ) return answer
                revertSqr /= 10
                if (revertSqr == 0) break
            }

        }
    }

    return answer
}
/**
 * Сложная (5 баллов)
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var counted: Int = 0 // Кол-во чисел, которое посчитано.
    var revertFib: Int = 0 // Зеркальное число Фибоначчи.
    var answer: Int = 0

    for (i in 1 until (n + 1)) {
        revertFib = revert(fib(i))

        while (true) {
            answer = revertFib % 10
            counted++

            if (counted == n) return answer

            revertFib /= 10
            if (revertFib == 0) break
        }
    }

    return answer
}
