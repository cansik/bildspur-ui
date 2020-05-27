package ch.bildspur.ui.fx

import ch.bildspur.color.RGB
import javafx.scene.paint.Color

fun Color.toRGB() : RGB {
    return RGB(this.red, this.green, this.blue, this.opacity)
}

fun RGB.toFXColor() : Color {
    return Color(this.r / 255.0, this.g / 255.0, this.b / 255.0, this.a.toDouble())
}