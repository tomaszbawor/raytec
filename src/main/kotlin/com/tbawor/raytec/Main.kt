package com.tbawor.raytec

import com.tbawor.raytec.ui.ApplicationWindow
import java.awt.EventQueue

fun main(args: Array<String>) {
    EventQueue.invokeLater(::createAndShowUi)
}

fun createAndShowUi() {
    val application = ApplicationWindow()
    application.startApp()
}
