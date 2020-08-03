package ch.bildspur.ui.html.properties

import ch.bildspur.ui.html.BaseHTMLElementProperty
import ch.bildspur.ui.properties.ActionParameter
import javafx.application.Platform
import javafx.scene.web.WebEngine
import java.lang.Exception
import java.lang.reflect.Field
import kotlin.concurrent.thread

class ActionProperty(field: Field, obj: Any, val annotation: ActionParameter)
    : BaseHTMLElementProperty("click", field, obj) {

    override fun onAction(engine: WebEngine) {
        val block = field.get(obj) as (() -> Unit)

        thread {
            try {
                block()
            } catch (ex : Exception) {
                error("${ex.message}")
            } finally {
                Platform.runLater {
                    if (annotation.invokesChange)
                        propertyChanged.invoke(this)
                }
            }
        }
    }
}