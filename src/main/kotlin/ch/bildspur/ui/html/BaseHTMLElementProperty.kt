package ch.bildspur.ui.html

import ch.bildspur.ui.html.utils.getElementId
import javafx.scene.web.WebEngine
import java.lang.reflect.Field

open class BaseHTMLElementProperty(val actionEvent : String, val field: Field, val obj: Any) : BaseHTMLProperty(field.getElementId()) {

    override fun bind(engine : WebEngine) {
        engine.executeScript("""
            java.log("startup");
            document.addEventListener("DOMContentLoaded", function(event) {
                java.log("document ready");
                document.getElementById("$elementId").addEventListener("$actionEvent", function(){
                    window.confirm("$elementId")
                })
            });
        """.trimIndent())
    }


    override fun unbind(engine: WebEngine) {
        TODO("Not yet implemented")
    }
}