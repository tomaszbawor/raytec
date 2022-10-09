package com.tbawor.raytec.ui

import java.awt.Canvas
import java.awt.Graphics
import java.awt.image.BufferedImage

class ImageComponent(private val image: BufferedImage) : Canvas() {

    init {
        setSize(image.width, image.height)
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.drawImage(image, 0, 0, null)
    }
}
