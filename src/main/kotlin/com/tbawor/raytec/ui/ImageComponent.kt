package com.tbawor.raytec.ui

import com.tbawor.raytec.BitmapStorage
import java.awt.Canvas
import java.awt.Graphics

class ImageComponent(private val image: BitmapStorage) : Canvas() {

    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.drawImage(image.image, 0, 0, null)
    }
}
