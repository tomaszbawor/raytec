package com.tbawor.raytec.ui

import com.tbawor.raytec.Renderer
import javax.swing.JButton
import javax.swing.JFrame

class ApplicationWindow : JFrame() {

    private val renderer = Renderer()

    init {
        title = "RayTec"
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }

    fun startApp() {
        val imageComponent = ImageComponent(renderer.imageBuffer)
        this.add(imageComponent)
        val button = JButton("Render")
        button.addActionListener {
            renderer.render()
            imageComponent.repaint()
        }
        this.pack()
        this.add(button, "South")
    }
}
