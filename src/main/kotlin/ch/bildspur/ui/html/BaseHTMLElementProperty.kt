package ch.bildspur.ui.html

import ch.bildspur.ui.html.utils.getElementId
import javafx.scene.web.WebEngine
import netscape.javascript.JSObject
import java.lang.reflect.Field

open class BaseHTMLElementProperty(val actionEvent : String, val field: Field, val obj: Any) : BaseHTMLProperty(field.getElementId()) {

    fun getElement(engine : WebEngine) : JSObject {
        return engine.executeScript("document.getElementById(\"$elementId\")") as JSObject
    }

    override fun bind(engine : WebEngine) {
        engine.executeScript("""
            // ready state
            function ready(fn) {
              if (document.readyState != 'loading'){
                fn();
              } else if (document.addEventListener) {
                document.addEventListener('DOMContentLoaded', fn);
              } else {
                document.attachEvent('onreadystatechange', function() {
                  if (document.readyState != 'loading')
                    fn();
                });
              }
            }
            
            ready(function() {
                // run change command
                document.getElementById("$elementId").addEventListener("$actionEvent", function(){
                    window.confirm("$elementId");
                    java.changed("$elementId");
                });
            });
        """.trimIndent())
    }


    override fun unbind(engine: WebEngine) {
        TODO("Not yet implemented")
    }
}