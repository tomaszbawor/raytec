package com.tbawor.raytec.ui

import com.tbawor.raytec.BitmapStorage
import java.awt.Canvas
import java.awt.Graphics

class ImageComponent(private val image: BitmapStorage) : Canvas() {

    init {
        setSize(image.width, image.height)
    }

    override fun paint(g: Graphics?) {
        g?.drawImage(image.image, 0, 0, null)
    }
}
