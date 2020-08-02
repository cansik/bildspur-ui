package ch.bildspur.ui.html.properties

import ch.bildspur.ui.html.BaseHTMLFieldProperty
import ch.bildspur.ui.html.BaseHTMLProperty
import ch.bildspur.ui.properties.ActionParameter
import java.lang.reflect.Field

class ActionProperty(field: Field, obj: Any, val annotation: ActionParameter) : BaseHTMLFieldProperty(field, obj) {
}