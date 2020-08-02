package ch.bildspur.ui.html.properties

import ch.bildspur.ui.html.BaseHTMLElementProperty
import ch.bildspur.ui.properties.ActionParameter
import java.lang.reflect.Field

class ActionProperty(field: Field, obj: Any, val annotation: ActionParameter)
    : BaseHTMLElementProperty("click", field, obj) {
}