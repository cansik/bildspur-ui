package ch.bildspur.ui.html.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.html.BaseHTMLElementProperty
import ch.bildspur.ui.properties.StringParameter
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class StringProperty(field: Field, obj: Any, val annotation: StringParameter)
    : BaseHTMLElementProperty("keydown", field, obj) {

    init {
        // setup binding
        val model = field.get(obj) as DataModel<Any>
        model.onChanged += {
            // set value
        }
        model.fireLatest()
    }
}