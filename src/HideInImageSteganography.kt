import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val imagePath = "c:/users/vector/desktop/cat.jpg"
    val outputPath = "c:/users/vector/desktop/secret-cat.jpg"
    val message = "Hello mom, I'm on the TV"
    encodeMessage(imagePath, outputPath, message)
    println("Message encoded successfully!")
    val decodedMessage = decodeMessage(outputPath)
    println("Decoded message: $decodedMessage")
}

fun encodeMessage(imagePath: String, outputPath: String, message: String) {
    val image: BufferedImage = ImageIO.read(File(imagePath))
    val binaryMessage = message.toByteArray().joinToString("") {
        it.toString(2).padStart(8, '0')
    } + "00000000"
    printEncodeMessage(imagePath, message, image)
    var messageIndex = 0
    for (y in 0..<image.height) {
        for (x in 0..<image.width) {
            if (messageIndex < binaryMessage.length) {
                val pixel = image.getRGB(x, y)
                val alpha = pixel shr 24 and 0xff
                val red = pixel shr 16 and 0xff
                val green = pixel shr 8 and 0xff
                val blue = pixel and 0xff
                val newBlue = (blue and 0xfe) or (binaryMessage[messageIndex].toString().toInt())
                val newPixel = (alpha shl 24) or (red shl 16) or (green shl 8) or newBlue
                image.setRGB(x, y, newPixel)
                printEncodeMessageInIteration(y, x, blue, newBlue, binaryMessage, messageIndex)
                messageIndex++
            }
        }
    }
    ImageIO.write(image, "png", File(outputPath))
}

fun decodeMessage(imagePath: String): String {
    val image = ImageIO.read(File(imagePath))
    val binaryMessage = StringBuilder()
    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val pixel = image.getRGB(x, y)
            val blue = pixel and 0xff
            binaryMessage.append(blue and 1)
        }
    }
    val byteMessage = binaryMessage.toString().chunked(8).map {
        it.toInt(2).toByte() }.toByteArray()
    return String(byteMessage).substringBefore('\u0000')
}

fun printEncodeMessage(imagePath: String, message: String, image: BufferedImage) {
    println("We obtain the image located in the following path:         $imagePath")
    println("The message we are to encode is:                           $message")
    println("Our encoded message is:                                    ${
        message.toByteArray().joinToString("") {
            it.toString(2).padStart(8, '0') } .chunked(8) .joinToString(" ")
    }")
    println("Additionally we append the following text to indicate the ending of our message: 00000000")
    println("In this case the height is: ${image.height}, and the length is: ${image.width}, total pixels: ${image.width*image.height}")
    println("We start iterating over each pixel of the image, using the height and width of the incoming image")
    println("-------------------------------------------------------------------------------")
}

fun printEncodeMessageInIteration(y: Int, x: Int, blue: Int, newBlue: Int, binaryMessage: String, messageIndex: Int) {
    println("The coordinates are:                                        $y and $x")
    println("Blue in this pixel is:                                      ${Integer.toBinaryString(blue)}")
    println("Message bit:                                                0000000${binaryMessage[messageIndex].toString().toInt()}")
    println("We set the least significant bit to the bit of out message: ${Integer.toBinaryString(newBlue)}")
    println("We generate a new pixel using our new blue value and we set it for the image")
    println("-------------------------------------------------------------------------------")
}