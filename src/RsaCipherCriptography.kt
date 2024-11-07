import java.math.BigInteger

fun main() {
    // Step 1: Choose two small prime numbers
    val encodedMessage = encodeStringToBigInteger("Hola")
    val p = BigInteger.valueOf(11533867)
    val q = BigInteger.valueOf(11533937)
    println("We choose our primes")
    println("Chosen primes:\n p = $p\n q = $q")

    // Step 2: Compute n = p * q
    val n = p.multiply(q)
    println("\nWe calculate n multiplying p * q")
    println("n: $n")

    val phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))
    println("\nWe calculate phi, using euler's equations phi(n) = (p-1) * (q-1)")
    println("phi(n): $phi")

    val e = BigInteger.valueOf(17)
    println("\nWe select our co-prime number respecting the rules 1 < e < phi(n)")
    println("Chosen e: $e")

    val d = e.modInverse(phi)
    println("\nWe compute d according to the formula d * e ≡ 1 mod((𝑝−1)×(𝑞−1))")
    println("Computed d: $d")

    // Public key (n, e) and private key (n, d)
    println("\nNow we have our public and private keys")
    println("Public key: (n = $n, e = $e)")
    println("Private key: (n = $n, d = $d)")

    // Step 6: Encrypt a message
    println("\nOriginal message: ${encodedMessage.toString().removeRange(0..0)}")
    val encrypted = BigInteger.valueOf(encodedMessage).modPow(e, n)
    println("Encrypted message: $encrypted")

    // Step 7: Decrypt the message
    val decrypted = encrypted.modPow(d, n)
    val decoded = decodeBigIntegerToString(decrypted)
    println("Decrypted message: $decoded")
}

fun encodeStringToBigInteger(input: String): Long {
    val stringBuilder = StringBuilder()
    stringBuilder.append(1)
    for (char in input) {
        val charToAppend = char.code.toString().padStart(3, '0')
        stringBuilder.append(charToAppend)
    }
    return stringBuilder.toString().toLong()
}

fun decodeBigIntegerToString(encoded: BigInteger): String {
    val encodedString = encoded.toString()
    val decodedStringBuilder = StringBuilder()
    var i = 0
    while (i < encodedString.length) {
        if (i == 0) {
            i++
            continue
        }
        val asciiValue = encodedString.substring(i, i + 3).toInt()
        decodedStringBuilder.append(asciiValue.toChar())
        i += 3
    }
    return decodedStringBuilder.toString()
}
