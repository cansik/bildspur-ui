package ch.bildspur.ui.html

import ch.bildspur.ui.properties.PropertyReader
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.web.WebView

class PropertyWebView(val reader : PropertyReader = PropertyReader(HTMLPropertyRegistry.properties)) : HBox() {
    val webView = WebView()
    val engine = webView.engine

    init {
        //webView.prefWidth = Double.MAX_VALUE
        setHgrow(webView, Priority.ALWAYS)
        children.add(webView)

        engine.load("https://bildspur.ch")
    }

    fun bind(obj : Any) {

    }
}