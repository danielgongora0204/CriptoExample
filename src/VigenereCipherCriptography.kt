fun main() {
    val plainText = "KUG is the best community"
    val key = "CSharpIsForLosers"
    val encryptedText = vigenereEncrypt(plainText, key)
    println("Encrypted Text: $encryptedText")
    val decryptedText = vigenereDecrypt(encryptedText, key)
    println("Decrypted Text: $decryptedText")
}

fun vigenereEncrypt(plainText: String, key: String): String {
    val keyRepeated = key.repeat((plainText.length / key.length) + 1).substring(0, plainText.length)
    printVigenereCipher(keyRepeated)
    return plainText.mapIndexed { index, char ->
        val shift = keyRepeated[index].uppercase().first() - 'A'
        println("Current iteration index:                        $index")
        println("Key-char in that index is:                      ${keyRepeated[index]}")
        println("Shift is equivalent to key-char index:          ${keyRepeated[index].uppercase().first() - 'A'}")
        println("Is char a letter?:                              ${char.isLetter()}")
        if (char.isLetter()) {
            val base = if (char.isUpperCase()) 'A' else 'a'
            val cipheredChar = ((char.code - base.code  + shift) % 26 + base.code).toChar()
            printVigenereCipherIteration(char, base, shift, cipheredChar)
            cipheredChar
        } else {
            char
        }
    }.joinToString("")
}

fun vigenereDecrypt(cipherText: String, key: String): String {
    val keyRepeated = key.repeat((cipherText.length / key.length) + 1).substring(0, cipherText.length)
    return cipherText.mapIndexed { index, char ->
        val shift = keyRepeated[index].uppercase().first() - 'A'
        if (char.isLetter()) {
            val base = if (char.isUpperCase()) 'A' else 'a'
            ((char - base - shift + 26) % 26 + base.toInt()).toChar()
        } else {
            char
        }
    }.joinToString("") }


fun printVigenereCipher(keyRepeated: String) {
    println("We extend our key to the length of the text to be ciphered: $keyRepeated")
    println("--------------------------------------------------------------------")
}

fun printVigenereCipherIteration(char: Char, base: Char, shift: Int, cipheredChar: Char) {
    println("Char to cipher:                                 $char")
    println("Base (depending on if the letter is uppercase): $base")
    println("Char to cipher - base to get the current index of the letter to encrypt: ${char.code} - ${base.code} = ${char.code - base.code}")
    println("Then we sum the shift:                          ${char.code - base.code} + $shift = ${(char - base + shift)}")
    println("This value will go through a modular operation of mod 26 (per each letter of the alphabet)")
    println("And the result will be summed the original base to get back its ASCII value")
    println("Operation:                                      ((${char.code} - ${base.code} + $shift) % 26 + ${base.code}) converted to char: $cipheredChar")
    println("--------------------------------------------------------------------")
}
