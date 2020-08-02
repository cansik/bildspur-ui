package ch.bildspur.ui.html.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.html.BaseHTMLElementProperty
import ch.bildspur.ui.properties.StringParameter
import javafx.scene.web.WebEngine
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class StringProperty(field: Field, obj: Any, val annotation: StringParameter)
    : BaseHTMLElementProperty("keydown", field, obj) {

    override fun bind(engine: WebEngine) {
        super.bind(engine)

        // setup binding
        val element = getElement(engine)
        val model = field.get(obj) as DataModel<Any>
        model.onChanged += {
            // set value
            element.setMember("value", it)
        }
        model.fireLatest()
    }
}