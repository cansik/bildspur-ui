package ch.bildspur.ui.html.properties

import ch.bildspur.ui.html.BaseHTMLProperty
import ch.bildspur.ui.properties.ActionParameter
import java.lang.reflect.Field

class ActionProperty(val field: Field, val obj: Any, val annotation: ActionParameter) : BaseHTMLProperty() {
}