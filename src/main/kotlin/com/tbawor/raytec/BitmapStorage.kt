package com.tbawor.raytec

import java.awt.Color
import java.awt.image.BufferedImage

class BitmapStorage(val width: Int, val height: Int) {
    val image = BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)

    fun setPixel(x: Int, y: Int, c: Color) {
        image.setRGB(x, y, c.rgb)
    }
}
