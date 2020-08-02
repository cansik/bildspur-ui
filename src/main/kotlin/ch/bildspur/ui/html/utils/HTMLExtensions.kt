package ch.bildspur.ui.html.utils

import ch.bildspur.ui.html.HTMLElement
import java.lang.reflect.Field

fun Field.getElementId() : String {
    return this.getAnnotation(HTMLElement::class.java).id
}