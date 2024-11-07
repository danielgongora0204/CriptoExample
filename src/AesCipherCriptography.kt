
@OptIn(ExperimentalUnsignedTypes::class)
fun main() {
    // the cipher key
    val key = ubyteArrayOf(
        'k'.code.toUByte(), 'k'.code.toUByte(), 'k'.code.toUByte(), 'k'.code.toUByte(),
        'e'.code.toUByte(), 'e'.code.toUByte(), 'e'.code.toUByte(), 'e'.code.toUByte(),
        'y'.code.toUByte(), 'y'.code.toUByte(), 'y'.code.toUByte(), 'y'.code.toUByte(),
        '.'.code.toUByte(), '.'.code.toUByte(), '.'.code.toUByte(), '.'.code.toUByte()
    )

    // the cipher key size
    val size = KeySize.SIZE_16

    // the plaintext
    val plaintext = ubyteArrayOf(
        'a'.code.toUByte(), 'b'.code.toUByte(), 'c'.code.toUByte(), 'd'.code.toUByte(),
        'e'.code.toUByte(), 'f'.code.toUByte(), '1'.code.toUByte(), '2'.code.toUByte(),
        '3'.code.toUByte(), '4'.code.toUByte(), '5'.code.toUByte(), '6'.code.toUByte(),
        '7'.code.toUByte(), '8'.code.toUByte(), '9'.code.toUByte(), '0'.code.toUByte()
    )

    // the ciphertext
    val ciphertext = UByteArray(16)

    // the decrypted text
    val decryptedtext = UByteArray(16)

    println("\nCipher Key (HEX format):")
    for (i in key.indices) {
        print("%02x".format(key[i].toInt()))
        if ((i + 1) % 16 != 0) { print(" ") } else { println() }
    }

    println("\nCipher Key (Plain Text):")
    for (i in key.indices) {
        print(key[i].toInt().toChar())
        if ((i + 1) % 16 != 0) { print(" ") } else { println() }
    }

    println("\nPlaintext (HEX format):")
    for (i in plaintext.indices) {
        print("%02x".format(plaintext[i].toInt()))
        if ((i + 1) % 16 != 0) { print(" ") } else { println() }
    }

    println("\nPlaintext (Plain text):")
    for (i in plaintext.indices) {
        print(plaintext[i].toInt().toChar())
        if ((i + 1) % 16 != 0) { print(" ") } else { println() }
    }

    // AES Encryption
    aesEncrypt(plaintext, ciphertext, key, size)
    println("\nCiphertext (HEX format):")
    for (i in ciphertext.indices) {
        print("%02x".format(ciphertext[i].toInt()))
        if ((i + 1) % 16 != 0) { print(" ") } else { println() }
    }

    println("\nCiphertext (Plain Text:")
    for (i in ciphertext.indices) {
        print(ciphertext[i].toInt().toChar())
        if ((i + 1) % 16 != 0) { print(" ") } else { println() }
    }

    // AES Decryption
    aesDecrypt(ciphertext, decryptedtext, key, size)
    println("\nDecrypted text (HEX format):")
    for (i in decryptedtext.indices) {
        print("%02x".format(decryptedtext[i].toInt()))
        if ((i + 1) % 16 != 0) { print(" ") } else { println() }
    }

    println("\nDecrypted text (Plain Text):")
    for (i in decryptedtext.indices) {
        print(decryptedtext[i].toInt().toChar())
        if ((i + 1) % 16 != 0) { print(" ") } else { println() }
    }
}

val sbox = arrayOf(
    // 0     1    2      3     4    5     6     7      8    9     A      B    C     D     E     F
    0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,  // 0
    0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,  // 1
    0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,  // 2
    0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,  // 3
    0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,  // 4
    0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,  // 5
    0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,  // 6
    0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,  // 7
    0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,  // 8
    0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,  // 9
    0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,  // A
    0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,  // B
    0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,  // C
    0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,  // D
    0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,  // E
    0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16   // F
)

val rsbox = arrayOf(
    0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb, 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb, 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e, 0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25, 0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92, 0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84, 0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06, 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b, 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73, 0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e, 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b, 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4, 0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f, 0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef, 0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61, 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d
)

val Rcon = arrayOf(
    0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
    0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
    0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
    0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d,
    0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab,
    0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d,
    0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25,
    0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01,
    0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d,
    0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa,
    0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a,
    0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02,
    0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
    0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
    0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
    0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
    0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f,
    0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5,
    0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33,
    0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb
)

enum class KeySize(val size: Int) {
    SIZE_16(16),
    SIZE_24(24),
    SIZE_32(32)
}

fun getSBoxValue(num: UByte): UByte {
    return sbox[num.toInt()].toUByte()
}

fun getSBoxInvert(num: UByte): UByte {
    return rsbox[num.toInt()].toUByte()
}

// Rijndael's key schedule rotate operation
// rotate the word eight bits to the left
@OptIn(ExperimentalUnsignedTypes::class)
fun rotate(word: UByteArray) {
    val c = word[0]
    for (i in 0..<3) {
        word[i] = word[i + 1]
    }
    word[3] = c
}

fun getRconValue(num: UByte): UByte {
    return Rcon[num.toInt()].toUByte()
}

@OptIn(ExperimentalUnsignedTypes::class)
fun core(word: UByteArray, iteration: Int) {
    // rotate the 32-bit word 8 bits to the left
    rotate(word)

    // apply S-Box substitution on all 4 parts of the 32-bit word
    for (i in 0..<4) {
        word[i] = getSBoxValue(word[i])
    }

    // XOR the output of the rcon operation with i to the first part (leftmost) only
    word[0] = word[0] xor getRconValue(iteration.toUByte())
}

// Rijndael's key expansion
// expands a 128, 192, 256 key into a 176, 208, 240 bytes key
@OptIn(ExperimentalUnsignedTypes::class)
fun expandKey(expandedKey: UByteArray, key: UByteArray, size: KeySize, expandedKeySize: Int) {
    var currentSize = 0
    var rconIteration = 1
    val t = UByteArray(4)

    // set the 16, 24, 32 bytes of the expanded key to the input key
    for (i in 0..<size.size) {
        expandedKey[i] = key[i]
    }
    currentSize += size.size

    while (currentSize < expandedKeySize) {
        // assign the previous 4 bytes to the temporary value t
        for (i in 0..<4) {
            t[i] = expandedKey[(currentSize - 4) + i]
        }
        // every 16, 24, 32 bytes we apply the core schedule to t
        // and increment rconIteration afterwards
        if (currentSize % size.size == 0) {
            core(t, rconIteration++)
        }
        // For 256-bit keys, we add an extra sbox to the calculation
        if (size == KeySize.SIZE_32 && (currentSize % size.size == 16)) {
            for (i in 0..<4) {
                t[i] = getSBoxValue(t[i])
            }
        }
        // We XOR t with the four-byte block 16, 24, 32 bytes before the new expanded key.
        // This becomes the next four bytes in the expanded key.
        for (i in 0..<4) {
            expandedKey[currentSize] = expandedKey[currentSize - size.size] xor t[i]
            currentSize++
        }
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun subBytes(state: UByteArray) {
    // substitute all the values from the state with the value in the SBox
    // using the state value as index for the SBox
    for (i in state.indices) {
        state[i] = getSBoxValue(state[i])
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun shiftRows(state: UByteArray) {
    // iterate over the 4 rows and call shiftRow() with that row
    for (i in 0..<4) {
        shiftRow(state, i)
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun shiftRow(state: UByteArray, nbr: Int) {
    for (i in 0..<nbr) {
        val tmp = state[0]
        for (j in 0..<3) {
            state[j] = state[j + 1]
        }
        state[3] = tmp
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun addRoundKey(state: UByteArray, roundKey: UByteArray) {
    for (i in state.indices) {
        state[i] = state[i] xor roundKey[i]
    }
}

fun galoisMultiplication(a: UByte, b: UByte): UByte {
    var aVar = a
    var bVar = b
    var p: UByte = 0u
    for (counter in 0..<8) {
        if ((bVar and 1u) == 1u.toUByte()) {
            p = p xor aVar
        }
        val hiBitSet = (aVar and 0x80u)
        aVar = (aVar.toInt() shl 1).toUByte()
        if (hiBitSet == 0x80u.toUByte()) {
            aVar = aVar xor 0x1bu
        }
        bVar = (bVar.toInt() shr 1).toUByte()
    }
    return p
}

@OptIn(ExperimentalUnsignedTypes::class)
fun mixColumns(state: UByteArray) {
    for (i in 0..<4) {
        val column = UByteArray(4)
        for (j in 0..<4) {
            column[j] = state[(j * 4) + i]
        }
        mixColumn(column)
        for (j in 0..<4) {
            state[(j * 4) + i] = column[j]
        }
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun mixColumn(column: UByteArray) {
    val cpy = column.copyOf()
    column[0] = galoisMultiplication(cpy[0], 2u) xor
            galoisMultiplication(cpy[3], 1u) xor
            galoisMultiplication(cpy[2], 1u) xor
            galoisMultiplication(cpy[1], 3u)

    column[1] = galoisMultiplication(cpy[1], 2u) xor
            galoisMultiplication(cpy[0], 1u) xor
            galoisMultiplication(cpy[3], 1u) xor
            galoisMultiplication(cpy[2], 3u)

    column[2] = galoisMultiplication(cpy[2], 2u) xor
            galoisMultiplication(cpy[1], 1u) xor
            galoisMultiplication(cpy[0], 1u) xor
            galoisMultiplication(cpy[3], 3u)

    column[3] = galoisMultiplication(cpy[3], 2u) xor
            galoisMultiplication(cpy[2], 1u) xor
            galoisMultiplication(cpy[1], 1u) xor
            galoisMultiplication(cpy[0], 3u)
}

@OptIn(ExperimentalUnsignedTypes::class)
fun aesRound(state: UByteArray, roundKey: UByteArray) {
    println("We substitute all bytes using our substitution map")
    subBytes(state)
    println("We shift our rows")
    shiftRows(state)
    println("We mix the columns")
    mixColumns(state)
    println("Finally we add the round key")
    addRoundKey(state, roundKey)
    println("------------------------------------------------------------------------------")
}

@OptIn(ExperimentalUnsignedTypes::class)
fun createRoundKey(expandedKey: UByteArray, roundKey: UByteArray) {
    for (i in 0..<4) {
        for (j in 0..<4) {
            roundKey[(i + (j * 4))] = expandedKey[(i * 4) + j]
        }
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun aesMain(state: UByteArray, expandedKey: UByteArray, nbrRounds: Int) {
    val roundKey = UByteArray(16)
    createRoundKey(expandedKey, roundKey)
    addRoundKey(state, roundKey)
    println("Before we start with the round with execute an XOR operation with a previously created round key")
    println("\nNumber of rounds encryption rounds: $nbrRounds")
    for (i in 1..<nbrRounds) {
        println("Round $i Iteration --------------------")
        createRoundKey(expandedKey.copyOfRange(16 * i, expandedKey.size), roundKey)
        println("\nWe create a key for the specific round")
        println("Round Key (HEX format):")
        for (j in roundKey.indices) {
            print("%02x".format(roundKey[j].toInt()))
            if ((j + 1) % 16 != 0) { print(" ") } else { println() }
        }

        println("\nRound Key (Plain text):")
        for (j in roundKey.indices) {
            print(roundKey[j].toInt().toChar())
            if ((j + 1) % 16 != 0) { print(" ") } else { println() }
        }

        aesRound(state, roundKey)
    }
    println("Last Round Iteration --------------------")
    createRoundKey(expandedKey.copyOfRange(16 * nbrRounds, expandedKey.size), roundKey)
    subBytes(state)
    shiftRows(state)
    addRoundKey(state, roundKey)
    println("\nWe create a key for the specific round")
    println("We substitute all bytes using our substitution map")
    println("We substitute all bytes using our substitution map")
    println("We shift our rows")
    println("Finally we add the round key")
    println("Finished Rounds --------------------")
}


enum class ErrorCode(val code: Int) {
    SUCCESS(0),
}

@OptIn(ExperimentalUnsignedTypes::class)
fun aesInvRound(state: UByteArray, roundKey: UByteArray) {
    invShiftRows(state)
    invSubBytes(state)
    addRoundKey(state, roundKey)
    invMixColumns(state)
}

@OptIn(ExperimentalUnsignedTypes::class)
fun invMixColumns(state: UByteArray) {
    for (i in 0..<4) {
        val column = UByteArray(4)
        for (j in 0..<4) {
            column[j] = state[(j * 4) + i]
        }
        invMixColumn(column)
        for (j in 0..<4) {
            state[(j * 4) + i] = column[j]
        }
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun invMixColumn(column: UByteArray) {
    val cpy = column.copyOf()
    column[0] = galoisMultiplication(cpy[0], 14u) xor
            galoisMultiplication(cpy[3], 9u) xor
            galoisMultiplication(cpy[2], 13u) xor
            galoisMultiplication(cpy[1], 11u)
    column[1] = galoisMultiplication(cpy[1], 14u) xor
            galoisMultiplication(cpy[0], 9u) xor
            galoisMultiplication(cpy[3], 13u) xor
            galoisMultiplication(cpy[2], 11u)
    column[2] = galoisMultiplication(cpy[2], 14u) xor
            galoisMultiplication(cpy[1], 9u) xor
            galoisMultiplication(cpy[0], 13u) xor
            galoisMultiplication(cpy[3], 11u)
    column[3] = galoisMultiplication(cpy[3], 14u) xor
            galoisMultiplication(cpy[2], 9u) xor
            galoisMultiplication(cpy[1], 13u) xor
            galoisMultiplication(cpy[0], 11u)
}


@OptIn(ExperimentalUnsignedTypes::class)
fun aesInvMain(state: UByteArray, expandedKey: UByteArray, nbrRounds: Int) {
    val roundKey = UByteArray(16)
    createRoundKey(expandedKey.copyOfRange(16 * nbrRounds, expandedKey.size), roundKey)
    addRoundKey(state, roundKey)

    for (i in nbrRounds - 1 downTo 1) {
        createRoundKey(expandedKey.copyOfRange(16 * i, expandedKey.size), roundKey)
        aesInvRound(state, roundKey)
    }

    createRoundKey(expandedKey.copyOfRange(0, expandedKey.size), roundKey)
    invShiftRows(state)
    invSubBytes(state)
    addRoundKey(state, roundKey)
}

@OptIn(ExperimentalUnsignedTypes::class)
fun invSubBytes(state: UByteArray) {
    for (i in state.indices) {
        state[i] = getSBoxInvert(state[i])
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun invShiftRows(state: UByteArray) {
    for (i in 0..<4) {
        invShiftRow(state, i)
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun invShiftRow(state: UByteArray, nbr: Int) {
    for (i in 0..<nbr) {
        val tmp = state[3]
        for (j in 3 downTo 1) {
            state[j] = state[j - 1]
        }
        state[0] = tmp
    }
}

@OptIn(ExperimentalUnsignedTypes::class)
fun aesEncrypt(input: UByteArray, output: UByteArray, key: UByteArray, size: KeySize): ErrorCode {
    val expandedKeySize: Int
    val block = UByteArray(16)

    val nbrRounds: Int = when (size) {
        KeySize.SIZE_16 -> 10
        KeySize.SIZE_24 -> 12
        KeySize.SIZE_32 -> 14
    }

    expandedKeySize = 16 * (nbrRounds + 1)
    val expandedKey = UByteArray(expandedKeySize)

    // Set the block values
    for (i in 0..<4) {
        for (j in 0..<4) {
            block[i + (j * 4)] = input[i * 4 + j]
        }
    }

    // Expand the key into a 176, 208, 240 bytes key
    println("------------------------------------------------------------------------------")
    println("\nFirst we expand the key")
    expandKey(expandedKey, key, size, expandedKeySize)

    println("Expanded Key (HEX format):")
    for (i in expandedKey.indices) {
        print("%02x".format(expandedKey[i].toInt()))
        if ((i + 1) % 16 != 0) { print(" ") } else { println() }
    }

    println("\nExpanded Key (Plain text):")
    for (i in expandedKey.indices) {
        print(expandedKey[i].toInt().toChar())
        if ((i + 1) % 16 != 0) { print(" ") } else { println() }
    }

    // Encrypt the block using the expandedKey
    aesMain(block, expandedKey, nbrRounds)

    // Unmap the block again into the output
    for (i in 0..<4) {
        for (j in 0..<4) {
            output[i * 4 + j] = block[i + (j * 4)]
        }
    }

    return ErrorCode.SUCCESS
}


@OptIn(ExperimentalUnsignedTypes::class)
fun aesDecrypt(input: UByteArray, output: UByteArray, key: UByteArray, size: KeySize): ErrorCode {
    val expandedKeySize: Int
    val block = UByteArray(16)

    val nbrRounds: Int = when (size) {
        KeySize.SIZE_16 -> 10
        KeySize.SIZE_24 -> 12
        KeySize.SIZE_32 -> 14
    }
    expandedKeySize = 16 * (nbrRounds + 1)
    val expandedKey = UByteArray(expandedKeySize)

    // Set the block values
    for (i in 0..<4) {
        for (j in 0..<4) {
            block[i + (j * 4)] = input[i * 4 + j]
        }
    }

    // Expand the key into a 176, 208, 240 bytes key
    expandKey(expandedKey, key, size, expandedKeySize)

    // Decrypt the block using the expandedKey
    aesInvMain(block, expandedKey, nbrRounds)

    // Unmap the block again into the output
    for (i in 0..<4) {
        for (j in 0..<4) {
            output[i * 4 + j] = block[i + (j * 4)]
        }
    }

    return ErrorCode.SUCCESS
}

