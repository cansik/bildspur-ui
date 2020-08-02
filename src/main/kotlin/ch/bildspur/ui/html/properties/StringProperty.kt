package ch.bildspur.ui.html.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.html.BaseHTMLProperty
import ch.bildspur.ui.properties.StringParameter
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class StringProperty(val field: Field, val obj: Any, val annotation: StringParameter) : BaseHTMLProperty() {

    init {
        // setup binding
        val model = field.get(obj) as DataModel<Any>
        model.onChanged += {
            // set value
        }
        model.fireLatest()
    }
}