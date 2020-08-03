package ch.bildspur.ui.html.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.html.BaseHTMLElementProperty
import ch.bildspur.ui.html.HTMLActionListener
import ch.bildspur.ui.properties.StringParameter
import javafx.scene.web.WebEngine
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class StringProperty(field: Field, obj: Any, val annotation: StringParameter)
    : BaseHTMLElementProperty("input", field, obj) {

    val model = field.get(obj) as DataModel<Any>

    override fun bind(engine: WebEngine, bindings: MutableMap<String, HTMLActionListener>) {
        super.bind(engine, bindings)

        // setup binding
        val element = getElement(engine)
        model.onChanged += {
            // set value
            element.setMember("value", it)
        }
        model.fireLatest()
    }

    override fun onAction(engine: WebEngine) {
        val element = getElement(engine)
        model.setSilent(element.getMember("value") as String)
        propertyChanged(this)
    }
}