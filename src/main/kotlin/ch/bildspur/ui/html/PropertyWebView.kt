package ch.bildspur.ui.html

import ch.bildspur.ui.properties.PropertyReader
import javafx.concurrent.Worker
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import netscape.javascript.JSObject

class PropertyWebView(val reader : PropertyReader = PropertyReader(HTMLPropertyRegistry.properties)) : HBox() {
    val webView = WebView()
    val engine: WebEngine = webView.engine

    val bindings = mutableMapOf<String, HTMLActionListener>()

    var model : Any = Any()

    private val bridge = object {
        fun log(msg : String) {
            println("JS: $msg")
        }

        fun changed(elementId: String) {
            bindings[elementId]?.onAction(engine)
        }
    }

    init {
        setHgrow(webView, Priority.ALWAYS)
        children.add(webView)
        engine.isJavaScriptEnabled = true
        webView.isContextMenuEnabled = false

        engine.loadWorker.stateProperty().addListener { ov, oldState, newState ->
            if(newState == Worker.State.SUCCEEDED) {
                injectBridge()
                bind(model)
            }
        }
    }

    fun injectBridge(bridge : Any = this.bridge) {
        // add methods
        val window = engine.executeScript("window") as JSObject
        window.setMember("java", bridge)
    }

    fun bind(model : Any) {
        reader.readPropertyAnnotations(model).forEach {
            if (it.property is BaseHTMLProperty) {
                it.property.bind(engine, bindings)
            }
        }
    }

    fun loadContent(content : String, model : Any = Any()) {
        this.model = model
        engine.loadContent(content)
    }

    fun load(url : String, model : Any = Any()) {
        this.model = model
        engine.load(url)
    }
}