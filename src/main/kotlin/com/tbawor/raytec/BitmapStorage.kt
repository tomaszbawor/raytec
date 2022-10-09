package com.tbawor.raytec

import java.awt.Color
import java.awt.image.BufferedImage

class BitmapStorage(private val width: Int, private val height: Int) {
    val image = BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)

    fun flip() {
        val temp = BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)
        for (y in 0 until height) {
            for (x in 0 until width) {
                temp.setRGB(x, y, image.getRGB(x, height - y - 1))
            }
        }
        image.graphics.drawImage(temp, 0, 0, null)
    }

    fun setPixel(x: Int, y: Int, c: Color) = image.setRGB(x, y, c.rgb)

    fun getPixel(x: Int, y: Int) = Color(image.getRGB(x, y))
}
