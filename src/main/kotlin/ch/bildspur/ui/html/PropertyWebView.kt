package ch.bildspur.ui.html

import ch.bildspur.ui.properties.PropertyReader
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.web.WebView
import netscape.javascript.JSObject

class PropertyWebView(val htmlUI : String, val reader : PropertyReader = PropertyReader(HTMLPropertyRegistry.properties)) : HBox() {
    val webView = WebView()
    val engine = webView.engine

    val bindings = mutableMapOf<String, HTMLActionListener>()

    init {
        //webView.prefWidth = Double.MAX_VALUE
        setHgrow(webView, Priority.ALWAYS)
        children.add(webView)

        engine.isJavaScriptEnabled = true

        // add methods
        val window = engine.executeScript("window") as JSObject
        window.setMember("java", object {
            fun log(msg : String) {
                println("JS: $msg")
            }

            fun changed(elementId: String) {
                println("Element Changed: $elementId")
                bindings[elementId]?.onAction(engine)
            }
        })

        engine.setConfirmHandler {
            println("confirm: $it")
            true
        }

        engine.loadContent(htmlUI)
    }

    fun bind(obj : Any) {
        reader.readPropertyAnnotations(obj).forEach {
            if (it.property is BaseHTMLProperty) {
                it.property.bind(engine, bindings)
            }
        }
    }
}