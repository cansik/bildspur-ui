package ch.bildspur.ui.fx.properties

import ch.bildspur.ui.properties.ActionParameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import javafx.application.Platform
import javafx.scene.Cursor
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import java.lang.Exception
import java.lang.reflect.Field
import kotlin.concurrent.thread

@Suppress("UNCHECKED_CAST")
class ActionProperty(field: Field, obj: Any, val annotation: ActionParameter) : BaseFXFieldProperty(field, obj) {
    val button = Button()
    val progress = ProgressIndicator()
    val errorText = Label()
    val box = HBox(button, progress, errorText)

    init {
        progress.isVisible = false
        progress.maxHeight = 20.0

        button.text = annotation.caption
        val block = field.get(obj) as (() -> Unit)

        errorText.isVisible = false
        errorText.textFill = Color.web("#FF0000")

        button.setOnAction {
            progress.isVisible = true
            errorText.isVisible = false
            button.isDisable = true

            val storedCursor = cursor
            cursor = Cursor.WAIT

            if(annotation.uiThread) {
                invoke(block, storedCursor)
            } else {
                thread(isDaemon = true) {
                    invoke(block, storedCursor)
                }
            }
        }

        box.spacing = 10.0
        children.add(box)
    }

    private fun invoke(block: () -> Unit, storedCursor: Cursor?) {
        try {
            block()
        } catch (ex : Exception) {
            errorText.isVisible = true
            errorText.text = "${ex.message}"
            ex.printStackTrace()
        } finally {
            Platform.runLater {
                cursor = storedCursor
                button.isDisable = false
                progress.isVisible = false
                if (annotation.invokesChange)
                    propertyChanged.invoke(this)
            }
        }
    }
}