package com.tbawor.raytec.ui

import com.tbawor.raytec.Renderer
import java.awt.image.ImageObserver
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JProgressBar
import javax.swing.SwingUtilities

class ApplicationWindow : JFrame(), ImageObserver {

    private val progressBar = JProgressBar(0, 100)
    private val renderer = Renderer {
        SwingUtilities.invokeLater { progressBar.value = it }
    }

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

        // Bottom panel
        val bottomPanel = JPanel()
        bottomPanel.add(button)
        bottomPanel.add(progressBar)

        this.add(bottomPanel, "South")
        this.pack()
    }
}
