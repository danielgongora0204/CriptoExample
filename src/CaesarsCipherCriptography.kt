fun main() {
    val originalText = "I really need a beer"
    val shift = 3
    println("Original: $originalText")
    val cipheredText = caesarCipher(originalText, shift)
    println("Ciphered: $cipheredText")
}

fun caesarCipher(input: String, shift: Int): String {
    val result = StringBuilder()
    printCaesarCipher(input, shift)
    for (char in input) {
        println("Next letter is:                                  $char")
        println("Is Char letter?:                                 ${char.isLetter()}")
        if (char.isLetter()) {
            val base = if (char.isUpperCase()) 'A' else 'a'
            val shiftedChar = ((char - base + shift) % 26 + base.code).toChar()
            printCaesarCipherIteration(char, base, shiftedChar, shift)
            result.append(shiftedChar)
        } else {
            println("Character is not a letter, so we can append directly")
            result.append(char)
        }
        println("--------------------------------------------------------------------")
    }
    return result.toString()
}

fun printCaesarCipher(input: String, shift: Int) {
    println("We start iterating over each letter in the word: $input")
    println("Our key (or shift):                              $shift")
    println("--------------------------------------------------------------------")
}

fun printCaesarCipherIteration(char: Char, base: Char, shiftedChar: Char, shift: Int) {
    println("The ASCII base is: $base with numerical value:   ${base.code}")
    println("Operation will be the numerical value of the base")
    println("Minus the numerical value of the char letter:    ${char.code}")
    println("Plus the shift:                                  $shift")
    println("This value will go through a modular operation of mod 26 (per each letter of the alphabet)")
    println("And the result will be summed the original base to get back its ASCII value")
    println("Operation: ((${char.code} - ${base.code} + $shift) % 26 + ${base.code}) converted to char: $shiftedChar")
}