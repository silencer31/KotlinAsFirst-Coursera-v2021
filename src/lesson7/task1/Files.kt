@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.util.*

// Урок 7: работа с файлами
// Урок интегральный, поэтому его задачи имеют сильно увеличенную стоимость
// Максимальное количество баллов = 55
// Рекомендуемое количество баллов = 20
// Вместе с предыдущими уроками (пять лучших, 3-7) = 55/103

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var currentLineLength = 0
    fun append(word: String) {
        if (currentLineLength > 0) {
            if (word.length + currentLineLength >= lineLength) {
                writer.newLine()
                currentLineLength = 0
            } else {
                writer.write(" ")
                currentLineLength++
            }
        }
        writer.write(word)
        currentLineLength += word.length
    }
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
            if (currentLineLength > 0) {
                writer.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(Regex("\\s+"))) {
            append(word)
        }
    }
    writer.close()
}

/**
 * Простая (8 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Некоторые его строки помечены на удаление первым символом _ (подчёркивание).
 * Перенести в выходной файл с именем outputName все строки входного файла, убрав при этом помеченные на удаление.
 * Все остальные строки должны быть перенесены без изменений, включая пустые строки.
 * Подчёркивание в середине и/или в конце строк значения не имеет.
 */
fun deleteMarked(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()

    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
        }
        else {
            if (line[0] != '_') {
                writer.write(line)
                writer.newLine()
            }
        }
    }

    writer.close()
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun numberOfOccurances(baseString: String, testString: String) : Int {
    var counter = 0
    val baseLen = baseString.length
    val testLen = testString.length
    var part: String
    var symbolsLeft: Int = baseLen
    var nextSymbolNumber: Int = 0

    if (testLen > baseLen) return 0

    if (baseString == testString) {
        return 1
    }

    while (true) {
        if (symbolsLeft < testLen) break

        part = baseString.substring( nextSymbolNumber, nextSymbolNumber + testLen )

        if (part == testString) {
            counter++
            nextSymbolNumber++
            symbolsLeft--
        }
        else {
            nextSymbolNumber++
            symbolsLeft--
        }
    }

    return counter
}

fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val fileStringsMap = mutableMapOf<String, Int>()
    val fileStrings = mutableListOf<String>()
    var counter: Int = 0
    var lowString: String

    for (line in File(inputName).readLines()) {
        fileStrings.add(line.lowercase(Locale.getDefault()))
    }

    for (subString in substrings) {
        counter = 0
        lowString = subString.lowercase(Locale.getDefault())

        for (fileLine in fileStrings) {
            if (fileLine.contains( lowString )) {
                counter += numberOfOccurances(fileLine, lowString)
            }
        }

        fileStringsMap[subString] = counter
    }

    return fileStringsMap.toMap()
}


/**
 * Средняя (12 баллов)
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val goodExclusives = listOf<String>("ЖЮри", "Жюри","жЮри","жюри","броШЮра", "броШюра", "брошЮра", "брошюра", "параШЮт", "параШют", "парашЮт", "парашют")
    val badExclusives  = listOf<String>("ЖУри", "Жури","жУри","жури","броШУра", "броШура", "брошУра", "брошура", "параШУт", "параШут", "парашУт", "парашут")

    val goodToChange = listOf<String>("жЮри","броШЮра", "параШют")
    val terribles    = listOf<String>("жУри","броШУра", "параШут")

    val listMistakes    = listOf<String>( "ШЮ", "Шю", "шЮ", "шю", "ШЯ", "Шя", "шЯ", "шя", "ЖЮ", "Жю", "жЮ", "жю","ЖЯ", "Жя", "жЯ", "жя", "ЖЫ", "Жы", "жЫ", "жы", "ШЫ", "Шы", "шЫ", "шы", "ЧЫ", "Чы", "чЫ", "чы", "ЧЯ", "Чя", "чЯ", "чя", "ЩЯ", "Щя", "щЯ", "щя", "ЩЫ", "Щы", "щЫ", "щы", "ЧЮ", "Чю", "чЮ", "чю", "ЩЮ", "Щю", "щЮ", "щю")
    val listCorrections = listOf<String>( "ШУ", "Шу", "шУ", "шу", "ША", "Ша", "шА", "ша", "ЖУ", "Жу", "жУ", "жу","ЖА", "Жа", "жА", "жа", "ЖИ", "Жи", "жИ", "жи", "ШИ", "Ши", "шИ", "ши", "ЧИ", "Чи", "чИ", "чи", "ЧА", "Ча", "чА", "ча", "ЩА", "Ща", "щА", "ща", "ЩИ", "Щи", "щИ", "щи", "ЧУ", "Чу", "чУ", "чу", "ЩУ", "Щу", "щУ", "щу")
    val writer = File(outputName).bufferedWriter()

    var correctedString: String

    for (line in File(inputName).readLines()) {
        correctedString = line

        for (i in listMistakes.indices) {
            if ( correctedString.contains(listMistakes[i]) ) {
                correctedString = correctedString.replace(listMistakes[i], listCorrections[i])
            }
        }

        for (i in badExclusives.indices) {
            if ( correctedString.contains(badExclusives[i]) ) {
                correctedString = correctedString.replace(badExclusives[i], goodExclusives[i])
            }
        }

        for (i in goodToChange.indices) {
            if ( correctedString.contains(goodToChange[i]) ) {
                correctedString = correctedString.replace(goodToChange[i], terribles[i])
            }
        }

        writer.write(correctedString)
        writer.newLine()
    }

    writer.close()
}

/**
 * Средняя (15 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val fileStrings = mutableListOf<String>()
    val writer = File(outputName).bufferedWriter()
    var theLongest = 0
    var delta = 0

    for (line in File(inputName).readLines()) {
       fileStrings.add(line.trim())
       if (line.trim().length > theLongest) theLongest = line.trim().length
    }

    for (line in fileStrings) {
        if ( line.length < theLongest ) {
            delta = (theLongest - line.length) / 2

            for( i in 0 until delta) {
                writer.write(" ")
            }
        }

        writer.write(line)
        writer.newLine()
    }

    writer.close()
}

/**
 * Сложная (20 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val fileStrings = mutableListOf<String>()
    var stringParts = listOf<String>()
    var correctedString: String = ""
    var lastSymbol: Char = ' '
    var theLongest = 0
    var totalSpaces = 0
    var averageSpaces = 0
    var restSpacesNumber = 0
    var numberOfBigSpaces = 0
    var wordsInLine = 0

    for (line in File(inputName).readLines()) {
        if (line.trim().isEmpty()) {
            fileStrings.add("")
            continue
        }

        correctedString = ""
        for (symbol in line.trim()) {
            if (symbol != ' ') {
                correctedString += symbol
                lastSymbol = symbol
            }
            else {
                if (lastSymbol != ' ') {
                    correctedString += symbol
                }

                lastSymbol = symbol
            }
        }

        fileStrings.add(correctedString)

        if (correctedString.length > theLongest) theLongest = correctedString.length
    }

    //println("The longest ${theLongest}")

    for (line in fileStrings) {
        if ( (line.length == theLongest) || (line.isEmpty()) || (!line.contains(' '))) {
            writer.write(line)
            writer.newLine()
            continue
        }

        stringParts = line.split(" ")
        wordsInLine = stringParts.size
        totalSpaces = theLongest - line.length + wordsInLine - 1
        averageSpaces = totalSpaces / (wordsInLine - 1)
        restSpacesNumber = totalSpaces - ((wordsInLine - 1) * averageSpaces)
        correctedString = ""

        for (i in 0 until wordsInLine) {
            correctedString += stringParts[i]

            if (i == (wordsInLine - 1)) break

            if (restSpacesNumber > 0) {
                for (j in 0 until (averageSpaces + 1)) {
                    correctedString += " "
                }

                restSpacesNumber--
            }
            else {
                for (j in 0 until averageSpaces) {
                    correctedString += " "
                }
            }

        }

        //println(correctedString.length)

        writer.write(correctedString)
        writer.newLine()
    }

    writer.close()
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 * Вернуть ассоциативный массив с числом слов больше 20, если 20-е, 21-е, ..., последнее слова
 * имеют одинаковое количество вхождений (см. также тест файла input/onegin.txt).
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val textWords = mutableListOf<String>()
    val uniqueMap = mutableMapOf<String, Int>()
    val unsortedMap = mutableMapOf<String, Int>()
    var word: String = ""

    for (line in File(inputName).readLines()) {
        word = ""
        for (symbol in line) {
            if (symbol.isLetter()) {
                word += symbol.lowercaseChar()
            }
            else {
                if (word.isNotEmpty()) {
                    textWords.add(word)
                    word = ""
                }
            }
        }

        if (word.isNotEmpty()) {
            textWords.add(word)
        }
    }

    val uniqueSet = textWords.toSet()


    for(value in uniqueSet) {
        unsortedMap[value] = textWords.count { it == value }
    }

    val result = unsortedMap.toList().sortedBy { (_, value) -> value}.reversed().toMap()

    /*for (entry in result) {
        print("Key: " + entry.key)
        println(" Value: " + entry.value)
    }*/

    if ( result.keys.size < 21) {
        return result
    }

    var counter = 0
    var lastNumber: Int = 0

    for((key, value) in result) {
        if (counter < 20) {
            uniqueMap[key] = value
            counter++

            if (counter == 20) lastNumber = value
        }
        else {
            if (value == lastNumber && lastNumber != 1) {
                uniqueMap[key] = value
            }
            else {
                break
            }
        }
    }

/*    for (entry in uniqueMap) {
        print("Key: " + entry.key)
        println(" Value: " + entry.value)
    }
*/
    return uniqueMap.toMap()
}

/**
 * Средняя (14 баллов)
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val rules = mutableMapOf<Char, String>()
    var correctedString: String = ""
    var bigLetters: String = ""

    for ((key, value) in dictionary) {
        if (key.isLetter()) {
            rules[key.lowercaseChar()] = value.lowercase()
        }
        else {
            rules[key] = value.lowercase()
        }

    }

    for (line in File(inputName).readLines()) {
        correctedString = ""

        for (symbol in line) {
            if (symbol.isLetter()) {
                if ( rules.keys.contains(symbol.lowercaseChar()) ) {
                    if (symbol.isLowerCase()) {
                        correctedString += rules[symbol]
                    }
                    else {

                            bigLetters = ""
                            bigLetters += rules[symbol.lowercaseChar()]

                            if (bigLetters.isNotEmpty()) {
                                if (bigLetters.length == 1) {
                                    bigLetters = bigLetters.uppercase()
                                }
                                else {
                                    bigLetters = bigLetters.substring(0, 1).uppercase() + bigLetters.substring(
                                        1,
                                        endIndex = bigLetters.length
                                    )
                                }
                            }
                            //println(bigLetters)
                            correctedString += bigLetters


                    }
                }
                else {
                    correctedString += symbol
                }
            }
            else {
                if ( rules.keys.contains(symbol)) {
                    correctedString += rules[symbol]
                }
                else {
                    correctedString += symbol
                }

            }
        }

        //println(correctedString)

        writer.write(correctedString)
        writer.newLine()
    }

    writer.close()
}

/**
 * Средняя (12 баллов)
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val originWords = mutableListOf<String>()
    val lowerWords  = mutableListOf<String>()
    val numbers = mutableListOf<Int>()
    var theLongest = 0
    var uniqueSize = 0

    for (line in File(inputName).readLines()) {
        uniqueSize = line.lowercase().toSet().size

        if (uniqueSize == line.length) {
            originWords.add(line)
            lowerWords.add(line.lowercase())
        }

        if ( (uniqueSize == line.length) && (uniqueSize > theLongest) ) {
            theLongest = uniqueSize
        }
    }

    if ( theLongest == 0 ) {
        writer.write("")
        writer.close()
        return
    }

    for(i in 0 until lowerWords.size) {
        if ( lowerWords[i].toSet().size == theLongest ) {
            numbers.add(i)
        }
    }

    if ( numbers.size == 1 ) {
        writer.write(originWords[ numbers[0] ])
    }
    else {
        for(j in 0 until numbers.size) {
            writer.write(originWords[ numbers[j] ])

            if (j < (numbers.size - 1)) {
                writer.write(", ")
            }
        }
    }

    writer.close()
}

/**
 * Сложная (22 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
    <body>
        <p>
            Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
            Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
        </p>
        <p>
            Suspendisse <s>et elit in enim tempus iaculis</s>.
        </p>
    </body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная (23 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
* Утка по-пекински
    * Утка
    * Соус
* Салат Оливье
    1. Мясо
        * Или колбаса
    2. Майонез
    3. Картофель
    4. Что-то там ещё
* Помидоры
* Фрукты
    1. Бананы
    23. Яблоки
        1. Красные
        2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
  <body>
    <p>
      <ul>
        <li>
          Утка по-пекински
          <ul>
            <li>Утка</li>
            <li>Соус</li>
          </ul>
        </li>
        <li>
          Салат Оливье
          <ol>
            <li>Мясо
              <ul>
                <li>Или колбаса</li>
              </ul>
            </li>
            <li>Майонез</li>
            <li>Картофель</li>
            <li>Что-то там ещё</li>
          </ol>
        </li>
        <li>Помидоры</li>
        <li>Фрукты
          <ol>
            <li>Бананы</li>
            <li>Яблоки
              <ol>
                <li>Красные</li>
                <li>Зелёные</li>
              </ol>
            </li>
          </ol>
        </li>
      </ul>
    </p>
  </body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная (30 баллов)
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
   19935
*    111
--------
   19935
+ 19935
+19935
--------
 2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
  235
*  10
-----
    0
+235
-----
 2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная (25 баллов)
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
  19935 | 22
 -198     906
 ----
    13
    -0
    --
    135
   -132
   ----
      3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

