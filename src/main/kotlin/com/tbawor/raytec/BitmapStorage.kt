package com.tbawor.raytec

import java.awt.Color
import java.awt.image.BufferedImage

class BitmapStorage(private val width: Int, private val height: Int) {
    private val image = BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)

    fun fill(c: Color) {
        val g = image.graphics
        g.color = c
        g.fillRect(0, 0, image.width, image.height)
    }

    fun setPixel(x: Int, y: Int, c: Color) = image.setRGB(x, y, c.rgb)

    fun getPixel(x: Int, y: Int) = Color(image.getRGB(x, y))
}
