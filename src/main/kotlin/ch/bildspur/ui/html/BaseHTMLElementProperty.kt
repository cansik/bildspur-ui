package ch.bildspur.ui.html

import ch.bildspur.ui.html.utils.getElementId
import javafx.scene.web.WebEngine
import netscape.javascript.JSObject
import java.lang.reflect.Field

abstract class BaseHTMLElementProperty(val actionEventName : String, val field: Field, val obj: Any)
    : BaseHTMLProperty(field.getElementId()), HTMLActionListener {

    fun getElement(engine : WebEngine) : JSObject {
        return engine.executeScript("document.getElementById(\"$elementId\")") as JSObject
    }

    override fun bind(engine : WebEngine, bindings : MutableMap<String, HTMLActionListener>) {
        engine.executeScript("""
            document.getElementById("$elementId").addEventListener("$actionEventName", function() {
                java.changed("$elementId");
            });
        """.trimIndent())

        bindings[elementId] = this
    }


    override fun unbind(engine: WebEngine, bindings : MutableMap<String, HTMLActionListener>) {
        bindings.remove(elementId)
        TODO("Not yet implemented")
    }
}