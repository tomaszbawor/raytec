package com.tbawor.raytec.ui

import com.tbawor.raytec.Renderer
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

class ApplicationWindow : JFrame() {

    private val renderer = Renderer()
    private val imageComponent = ImageComponent(renderer.imageBuffer)

    init {
        title = "RayTec"
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
        this.add(imageComponent)
        val button = JButton("Render")
        button.addActionListener {
            renderImage()
        }

        // Bottom panel
        val bottomPanel = JPanel().apply {
            add(button)
        }

        this.add(bottomPanel, "South")
        this.pack()
    }

    private fun renderImage() {
        renderer.render()
        imageComponent.repaint()
    }
}
