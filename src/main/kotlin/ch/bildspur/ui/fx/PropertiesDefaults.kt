package ch.bildspur.ui.fx

import javafx.scene.control.Label
import javafx.scene.text.Font

fun Label.styleValueLabel() {
    this.minWidth = 50.0
    this.maxWidth = 50.0
    this.font = Font("Helvetica", 12.0)
}