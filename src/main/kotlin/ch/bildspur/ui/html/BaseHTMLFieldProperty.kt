package ch.bildspur.ui.html

import ch.bildspur.ui.html.utils.getElementId
import java.lang.reflect.Field

open class BaseHTMLFieldProperty(val field: Field, val obj: Any) : BaseHTMLProperty(field.getElementId()) {
}