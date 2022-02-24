@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import java.lang.Integer.min
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

// Урок 2: ветвления (здесь), логический тип (см. 2.2).
// Максимальное количество баллов = 6
// Рекомендуемое количество баллов = 5
// Вместе с предыдущими уроками = 9/12

/**
 * Пример
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}

/**
 * Простая (2 балла)
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String = when {
    age < 0 -> "Error"
    age > 200 -> "Error"
    (age in 5..20) -> "$age лет"
    (age % 100 in 5..20) -> "$age лет"
    (age % 10 == 1) -> "$age год"
    (age % 10 in 2..4) -> "$age года"
    else -> "$age лет"
}

/**
 * Простая (2 балла)
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(
    t1: Double, v1: Double,
    t2: Double, v2: Double,
    t3: Double, v3: Double
): Double {
    val firstPart = t1 * v1
    val secondPart = t2 * v2
    val thirdPart = t3 * v3

    val halfWay = (firstPart + secondPart + thirdPart) / 2

    if (halfWay <= firstPart) {
        return halfWay / v1
    }

    if (halfWay <= (firstPart + secondPart)) {
        return t1 + ((halfWay - firstPart) / v2)
    }

    return t1 + t2 + ((halfWay - firstPart - secondPart) / v3)
}

/**
 * Простая (2 балла)
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(
    kingX: Int, kingY: Int,
    rookX1: Int, rookY1: Int,
    rookX2: Int, rookY2: Int
): Int =
    when {
        ((rookX1 == kingX) && (rookY2 == kingY)) -> 3
        ((rookX2 == kingX) && (rookY1 == kingY)) -> 3
        ((rookX1 == kingX) || (rookY1 == kingY)) -> 1
        ((rookX2 == kingX) || (rookY2 == kingY)) -> 2
        else -> 0
    }


/**
 * Простая (2 балла)
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(
    kingX: Int, kingY: Int,
    rookX: Int, rookY: Int,
    bishopX: Int, bishopY: Int
): Int = when {
    (((kingX + kingY) == (bishopX + bishopY)) && ((kingX == rookX) || (kingY == rookY))) -> 3
    (((bishopX - kingX) == (bishopY - kingY)) && ((kingX == rookX) || (kingY == rookY))) -> 3
    (((kingX + kingY) == (bishopX + bishopY)) || ((bishopX - kingX) == (bishopY - kingY))) -> 2
    ((rookX == kingX) || (rookY == kingY)) -> 1
    else -> 0
}

/**
 * Простая (2 балла)
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    val longSide: Double
    val side_one: Double
    val side_two: Double

    if (a >= b) {
        if (c >= a) {
            longSide = c
            side_one = a
            side_two = b
        } else {
            longSide = a
            side_one = b
            side_two = c
        }
    } else {
        if (c >= b) {
            longSide = c
            side_one = a
            side_two = b
        } else {
            longSide = b
            side_one = a
            side_two = c
        }
    }

    if (side_one + side_two <= longSide) return -1

    val cosA: Double = (side_one.pow(2) + side_two.pow(2) - longSide.pow(2)) / (2 * side_one * side_two)

    return when {
        cosA == 0.0 -> 1
        cosA > 0 -> 0
        else -> 2
    }
}

/**
 * Средняя (3 балла)
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
    if (c > b) return -1 // Второй отрезок уехал за первый
    if (a > d) return -1 // Первый отрезок уехал за второй
    return if (a <= c) { // Есть перекрытие
        min(b, d) - c
    } else {
        min(b, d) - a
    }
}
