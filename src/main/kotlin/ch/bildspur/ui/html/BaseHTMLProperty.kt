package ch.bildspur.ui.html

import ch.bildspur.event.Event
import ch.bildspur.ui.properties.BaseProperty
import javafx.scene.web.WebEngine
import org.w3c.dom.Element

abstract class BaseHTMLProperty(val elementId : String) : BaseProperty {
    override val propertyChanged = Event<BaseProperty>()

    @Volatile override var isSetup = false

    override fun preventFirstTime(block : () -> Unit) {
        if(isSetup)
            block()

        isSetup = true
    }

    fun getElementById(engine : WebEngine) : Element {
        return engine.document.getElementById(elementId)
    }

    abstract fun bind(engine : WebEngine, bindings : MutableMap<String, HTMLActionListener>)
    abstract fun unbind(engine : WebEngine, bindings : MutableMap<String, HTMLActionListener>)
}