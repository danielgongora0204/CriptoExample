fun main() {
    val listOfRotors = listOf(
        Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ"),
        Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE"),
        Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO")
    )
    val reflector = Reflector("YRUHQSLDPXNGOKMIEBFZCWVJAT")
    val plugBoard = Plugboard(mapOf('A' to 'B', 'B' to 'A', 'C' to 'D', 'D' to 'C'))
    val enigma = EnigmaMachine(listOfRotors, reflector, plugBoard)
    val message = "Its 2 am and Im falling asleep"
    println("We start iterating per each letter of our plain text message: $message")
    val encodedMessage = enigma.encode(message)
    println("End Message Iteration --------------------------------------------------------------------")
    println("Encoded Message: $encodedMessage")
}

class EnigmaMachine(private val rotors: List<Rotor>, private val reflector: Reflector, private val plugboard: Plugboard ) {
    fun encode(message: String): String {
        return message.map { char ->
            println("Message Iteration --------------------------------------------------------------------")
            println("Is Char letter?: ${char.isLetter()}")
            if (char.isLetter()) {
                var encodedChar = plugboard.swap(char.uppercase().first())
                println("First we swap our current char: $char using plug board: $encodedChar")
                println("We pass our char through each rotor forward")
                rotors.forEachIndexed { index, rotor ->
                    val beforeEncoding = encodedChar
                    encodedChar = rotor.encodeForward(encodedChar)
                    println("Rotor iteration-----------------------------------------------------------")
                    println("On our rotor n.${index + 1} we encode: $beforeEncoding to $encodedChar")
                }
                println("End Rotor iteration-----------------------------------------------------------")
                println("We pass through the reflector")
                val beforeReflection = encodedChar
                encodedChar = reflector.reflect(encodedChar)
                println("We encode $beforeReflection to $encodedChar")
                println("We pass our char through each rotor in reverse")
                rotors.reversed().forEachIndexed { index, rotor ->
                    println("Rotor iteration-----------------------------------------------------------")
                    val beforeEncoding = encodedChar
                    encodedChar = rotor.encodeBackward(encodedChar)
                    println("On our rotor n.${rotors.size - index} we encode: $beforeEncoding to $encodedChar")
                }
                val beforeLastPlugBoard = encodedChar
                encodedChar = plugboard.swap(encodedChar)
                println("End rotor iteration-----------------------------------------------------------")
                println("Pass once more through our plug board: $beforeLastPlugBoard to $encodedChar")
                rotors[0].rotate()
                encodedChar
            } else {
                char
            }
        }.joinToString("")
    }
}

class Rotor(val wiring: String, var position: Int = 0) {
    private val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun encodeForward(c: Char): Char {
        println(alphabet)
        println(wiring)
        val index = (alphabet.indexOf(c) + position) % 26
        println("${" ".repeat(index)}^")
        return wiring[index]
    }

    fun encodeBackward(c: Char): Char {
        println(wiring)
        println(alphabet)
        val index = (wiring.indexOf(c) - position + 26) % 26
        println("${" ".repeat(index)}^")
        return alphabet[index]
    }

    fun rotate() {
        position = (position + 1) % 26
        println("Rotate first rotor by: $position")
    }
}

class Reflector(val wiring: String) {
    fun reflect(c: Char): Char {
        return wiring["ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(c)]
    }
}

class Plugboard(val wiring: Map<Char, Char>) {
    fun swap(c: Char): Char {
        return wiring[c] ?: c
    }
}