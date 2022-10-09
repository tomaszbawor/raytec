package com.tbawor.raytec

import com.tbawor.raytec.ui.ImageComponent
import java.awt.EventQueue
import javax.swing.JFrame
import javax.swing.Renderer

fun main(args: Array<String>) {
    EventQueue.invokeLater(::createAndShowUi)
}

fun createAndShowUi() {
    val frame = JFrame("UI example")
    val renderer = Renderer()
    frame.add(ImageComponent(renderer.imageBuffer))
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.isVisible = true
    frame.setSize(renderer.IMAGE_WIDTH, renderer.IMAGE_HEIGHT)
    renderer.render()
    frame.repaint()
}
