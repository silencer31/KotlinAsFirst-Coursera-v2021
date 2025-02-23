@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import ru.spbstu.wheels.uncheckedCast

// Урок 5: ассоциативные массивы и множества
// Максимальное количество баллов = 14
// Рекомендуемое количество баллов = 9
// Вместе с предыдущими уроками = 33/47

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая (2 балла)
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val reverseMap = mutableMapOf<Int, List<String>>()
    val marks = grades.values.toSet()
    val namesList = mutableListOf<String>()

    for (uniqueMark in marks) {
        for ((name, mark) in grades) {
            if (uniqueMark == mark) {
                namesList.add(name)
            }
        }

        reverseMap[uniqueMark] = namesList.toList()
        namesList.clear()
    }

    return reverseMap.toMap()
}

/**
 * Простая (2 балла)
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    val aKeysNumber = a.keys.size
    var keysFound: Int = 0

    for((aKey, aValue) in a) {
        for((bKey, bValue) in b) {
            if (aKey == bKey) {
                keysFound++
                if ( aValue != bValue) return false
            }
        }
    }

    if (keysFound == aKeysNumber) return true

    return false
}

/**
 * Простая (2 балла)
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    val keysToRemove = mutableListOf<String>()

    for ((aKey, aValue) in a) {
        for ((bKey, bValue) in b) {
            if(aKey == bKey) {
                if (aValue == bValue) {
                    keysToRemove.add(aKey)
                }
            }
        }
    }

    for (key in keysToRemove) {
        a.remove(key)
    }
}

/**
 * Простая (2 балла)
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяющихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    val namesSet = mutableSetOf<String>()

    for (aName in a) {
        if (b.contains(aName)) {
            namesSet.add(aName)
        }
    }

    return namesSet.toList()
}

/**
 * Средняя (3 балла)
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val resultSet = mapA.keys + mapB.keys
    val valuesList = mutableListOf<String>()
    val resultMap = mutableMapOf<String, String>()

    for (key in resultSet) {
        for ((aKey, aValue) in mapA) {
            if (key == aKey) {
                if (!valuesList.contains(aValue)) {
                    valuesList.add(aValue)
                }
            }
        }

        for ((bKey, bValue) in mapB) {
            if (key == bKey) {
                if (!valuesList.contains(bValue)) {
                    valuesList.add(bValue)
                }
            }
        }

        resultMap[key] = valuesList.joinToString(", ")

        valuesList.clear()
    }

    return resultMap.toMap()
}

/**
 * Средняя (4 балла)
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val resultMap = mutableMapOf<String, Double>()
    val stockNames = mutableSetOf<String>()

    for ((first) in stockPrices) {
        stockNames.add(first)
    }

    var count: Int = 0
    var value: Double = 0.0

    for (stock in stockNames) {
        count = 0
        value = 0.0

        for ((first, second) in stockPrices) {
            if (first == stock) {
                value += second
                count++
            }
        }

        resultMap[stock] = value / count
    }

    return resultMap.toMap()
}

/**
 * Средняя (4 балла)
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    val typesSet = mutableSetOf<String>()

    for ((first, second) in stuff) {
        typesSet.add(second.first)
    }

    if (!typesSet.contains(kind)) {
        return null
    }

    var theCheapest: String = ""
    var price: Double = 0.0

    for ((first, second) in stuff) {
        if (second.first == kind) {
            if (price == 0.0) {
                theCheapest = first
                price = second.second
            }
            else {
                if (second.second < price) {
                    theCheapest = first
                    price = second.second
                }
            }
        }
    }

    return theCheapest
}

/**
 * Средняя (3 балла)
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    val lettersSet = mutableSetOf<Char>()
    val lowerChars = mutableListOf<Char>()

    for(letter in chars){
        lowerChars.add(letter.lowercaseChar())
    }

    for (letter in word) {
        lettersSet.add(letter.lowercaseChar())
    }

    for(letter in lettersSet) {
        if (!lowerChars.contains(letter)) return false
    }

    return true
}

/**
 * Средняя (4 балла)
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val lettersSet = mutableSetOf<String>()
    val resultMap = mutableMapOf<String, Int>()
    var count: Int = 0

    for (letter in list) {
        lettersSet.add(letter)
    }

    for (letter in lettersSet) {
        count = 0
        for (value in list) {
            if (letter == value) {
                count++
            }
        }

        if (count > 1) {
            resultMap[letter] = count
        }
    }

    return resultMap.toMap()
}

/**
 * Средняя (3 балла)
 *
 * Для заданного списка слов определить, содержит ли он анаграммы.
 * Два слова здесь считаются анаграммами, если они имеют одинаковую длину
 * и одно можно составить из второго перестановкой его букв.
 * Скажем, тор и рот или роза и азор это анаграммы,
 * а поле и полено -- нет.
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun compareStringsAnagr(wordOne: String, wordTwo: String) : Boolean {
    val lettersSet = mutableSetOf<Char>()

    for(letter in wordOne) {
        lettersSet.add(letter)
    }

    for (letter in lettersSet) {
        if ( wordOne.count{it ==letter} != wordTwo.count{it == letter} ) {
            return false
        }
    }

    return true
}

fun hasAnagrams(words: List<String>): Boolean {
    for(i in words.indices) {
        for (j in (i + 1) until words.size) {
            if (words[i].length == words[j].length) {
                if (compareStringsAnagr(words[i], words[j])) {
                    return true
                }
            }
        }
    }

    return false
}

/**
 * Сложная (5 баллов)
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 *
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Оставлять пустой список знакомых для людей, которые их не имеют (см. EvilGnome ниже),
 * в том числе для случая, когда данного человека нет в ключах, но он есть в значениях
 * (см. GoodGnome ниже).
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta"),
 *       "Friend" to setOf("GoodGnome"),
 *       "EvilGnome" to setOf()
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat"),
 *          "Friend" to setOf("GoodGnome"),
 *          "EvilGnome" to setOf(),
 *          "GoodGnome" to setOf()
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val hands = mutableMapOf<String, Set<String>>()

    val uniqueNames = mutableSetOf<String>()
    val lonely      = mutableSetOf<String>()
    val notSure     = mutableSetOf<String>()


    for((key, value) in friends) {
        if (value.isEmpty()) {
            lonely.add(key)
        }
        else {
            uniqueNames.add(key)

            for(name in value) {
                notSure.add(name)
            }
        }
    }

    for(name in notSure) {
        if ( !uniqueNames.contains(name)) {
            lonely.add(name)
        }
    }

    for (name in uniqueNames) {
        val connections = mutableSetOf<String>()

        val drugi = friends[name] ?: continue

        for (value in drugi) {
            connections.add(value)
        }

        for (value in drugi) {
            val farFriends = friends[value] ?: continue

            for (person in farFriends) {
                if (person != name)
                    connections.add(person)
            }
        }

        while (true) {
            val oldSize = connections.size
            val farFriendsCopy = mutableSetOf<String>()

            for (person in connections) {
                farFriendsCopy.add(person)
            }

            for (value in farFriendsCopy) {
                val veryFarFriends = friends[value] ?: continue

                for (person in veryFarFriends) {
                    if (person != name)
                        connections.add(person)
                }
            }

            if (connections.size == oldSize) break
        }

        hands[name] = connections
    }

    for (name in lonely) {
        hands[name] = setOf()
    }

    return hands.toMap()
}

/**
 * Сложная (6 баллов)
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    if (list.size < 2) return Pair(-1, -1)

    var indexOne = 0

    for(i in 0 until list.size) {
        indexOne = i
        for(j in 0 until list.size) {
            if (j == indexOne) continue

            if (list[indexOne] + list[j] == number) {
                return Pair(indexOne, j)
            }
        }
    }

    return Pair(-1, -1)
}

/**
 * Очень сложная (8 баллов)
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    if (treasures.isEmpty()) return emptySet()

    var totalPrice: Long = 0
    var totalWeight: Long = 0

    val acceptables = mutableMapOf<String, Pair<Int, Int>>()
    treasures.forEach {
        if (it.value.first <= capacity) {
            acceptables[it.key] = Pair(it.value.first, it.value.second)
            totalPrice += it.value.second
            totalWeight += it.value.first
        }
    }

    if (acceptables.isEmpty()) return emptySet()

    val names = mutableListOf<String>()
    val weights = mutableListOf<Int>()
    val prices = mutableListOf<Int>()
    val indexes = mutableListOf<Int>()
    var ind = 0


    acceptables.forEach {
        names.add(it.key)
        weights.add(it.value.first)
        prices.add(it.value.second)
        indexes.add(ind)
        ind++
    }


    /* fun testOutput(bigSet: Set<Set<Int>>) {
        bigSet.forEach {
            println("$it   price ${combinationTotalPrice(it)}")
        }
    }*/

    fun reduceCombination(pairWithSetToReduce: Pair<Set<Int>, Pair<Long, Long>>): Set<Pair<Set<Int>, Pair<Long, Long>>> {
        val setOfReducedSets = mutableSetOf<Pair<Set<Int>, Pair<Long, Long>>>()
        val setToReduce = pairWithSetToReduce.first

        setToReduce.reversed().forEach {
            val workSet = setToReduce.toMutableSet()
            workSet.remove(it)

            val newCombination = Pair(
                workSet.toSet(),
                Pair(pairWithSetToReduce.second.first - weights[it], pairWithSetToReduce.second.second - prices[it])
            )

            setOfReducedSets.add(newCombination)
        }

        return setOfReducedSets.toSet()
    }

    val initialCombination = Pair(indexes.toSet(), Pair(totalWeight, totalPrice))

    val summarySet = mutableSetOf<Pair<Set<Int>, Pair<Long, Long>>>()
    summarySet += initialCombination
    var currentSetToCheck = summarySet.toSet()

    var bestPrice = 0L


    var bestCombinaton = setOf<Int>()

    for (i in 0 until indexes.size) {
        currentSetToCheck.forEach {
            //println(it)
            if (it.second.first <= capacity) {
                if (it.second.second > bestPrice) {
                    bestPrice = it.second.second
                    bestCombinaton = it.first
                }
            }
        }

        if (bestPrice > 0) break

        val newReducedSets = mutableSetOf<Pair<Set<Int>, Pair<Long, Long>>>()

        currentSetToCheck.forEach {
            newReducedSets += reduceCombination(it)
        }

        currentSetToCheck = newReducedSets
    }


    val result = mutableSetOf<String>()

    bestCombinaton.forEach {
        result += names[it]
    }

    return result.toSet()
}

/*
fun checkCombination(set: Set<Int>): Int {
        var totalWeight = 0
        var totalPrice = 0

        set.forEach{
            totalWeight += weights[it]
            if (totalWeight > capacity) return -1
            totalPrice += prices[it]
        }

        return totalPrice
    }

    fun isCombinationPossible(set: Set<Int>): Boolean {
        var total = 0
        set.forEach{
            total += weights[it]
            if (total > capacity) return false
        }

        return true
    }

    fun combinationTotalPrice(set: Set<Int>): Int {
        var total = 0
        set.forEach{
            total += prices[it]
        }
        return total
    }

    // Проверим, возможно ли взять все
    if ( isCombinationPossible(indexes.toSet()) ) {
        return names.toSet()
    }
 */

/*
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    if (treasures.isEmpty()) return emptySet()

    val acceptables = mutableMapOf<String, Pair<Int, Int>>()
    treasures.forEach{
        if (it.value.first <= capacity ) {
            acceptables[it.key] = Pair(it.value.first, it.value.second)
        }
    }

    if (acceptables.isEmpty()) return emptySet()

    val sorted = acceptables.toList().sortedBy { (_, value) -> value.first}.toMap()

    val names = mutableListOf<String>()
    val weights = mutableListOf<Int>()
    val prices  = mutableListOf<Int>()
    val indexes  = mutableListOf<Int>()
    var ind = 0

    sorted.forEach{
        names.add(it.key)
        weights.add(it.value.first)
        prices.add(it.value.second)
        indexes.add(ind)
        ind++
    }
//println(indexes)
    /*
    1. Создавать комбинацию уникальных индексов сокровищ
    2. Проверять, возможно ли размещение такой комбинации в рюкзаке
    3. Если возможно, вычислять её сумму и сравнивать с прошлой максимальной суммой.
    4. Если новая сумма больше, то запоминать новую сумму и комбинацию
    5. В конце вернуть самую выгодную
     */

    fun isCombinationPossible(set: Set<Int>): Boolean {
        var total = 0
        set.forEach{
            total += weights[it]
            if (total > capacity) return false
        }

        return true
    }

    fun combinationTotalPrice(set: Set<Int>): Int {
        var total = 0
        set.forEach{
            total += prices[it]
        }
        return total
    }

    // Проверим, возможно ли взять все
    if ( isCombinationPossible(indexes.toSet()) ) {
        return names.toSet()
    }

    fun getOneItemCombinations(): Set<Set<Int>> {
        val bigSet = mutableSetOf<Set<Int>>()

        for(i in 0 until indexes.size) {
            val workSet = setOf<Int>(indexes[i])
            bigSet.add(workSet)
        }

        return bigSet.toSet()
    }

    fun expandCombination(set: Set<Int>): Set<Set<Int>> {
        val bigSet = mutableSetOf<Set<Int>>()

        for(i in 0 until indexes.size) {
            val workSet = set.toMutableSet()
            if (workSet.contains(indexes[i])) continue
            workSet.add(indexes[i])

            if ( isCombinationPossible(workSet.toSet())) {
                bigSet.add(workSet)
            }
        }

        return bigSet.toSet()
    }

    fun testOutput(bigSet: Set<Set<Int>>) {
        bigSet.forEach {
            println("$it   price ${combinationTotalPrice(it)}")
        }
    }

    val summarySet = mutableSetOf<Set<Int>>()
    summarySet += getOneItemCombinations()

    var previousCombinations = summarySet

    //testOutput(previousCombinations)

    for(i in 0 until (indexes.size - 2)) {
        val bigExpansion = mutableSetOf<Set<Int>>()

        previousCombinations.forEach {
            val expansion = expandCombination( it )
            //testOutput(expansion)
            bigExpansion += expansion
        }

        previousCombinations = bigExpansion
        summarySet += bigExpansion
    }

    var bestCombinaton = setOf<Int>()
    var bestPrice = 0
    var previousPrice = 0

    testOutput(summarySet)

    summarySet.forEach {
        previousPrice = combinationTotalPrice( it)
        if ( previousPrice > bestPrice) {
            bestPrice = previousPrice
            bestCombinaton = it
        }
    }

    val result = mutableSetOf<String>()
    bestCombinaton.forEach {
        result += names[it]
    }

    return result.toSet()
}

 */