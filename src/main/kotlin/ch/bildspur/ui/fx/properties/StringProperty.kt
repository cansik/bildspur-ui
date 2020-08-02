package ch.bildspur.ui.fx.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.fx.controls.EditTextField
import ch.bildspur.ui.properties.StringParameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import javafx.application.Platform
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class StringProperty(field: Field, obj: Any, val annotation: StringParameter) : BaseFXFieldProperty(field, obj) {

    val textField = EditTextField()

    init {
        setHgrow(textField, Priority.ALWAYS)
        textField.isEditable = annotation.isEditable
        applyStyle()

        children.add(textField)

        // setup binding
        val model = field.get(obj) as DataModel<Any>
        model.onChanged += {
            Platform.runLater {
                textField.text = model.value.toString()
            }
        }
        model.fireLatest()

        textField.setOnAction {
            model.value = textField.text
            propertyChanged(this)
            applyStyle()
        }
    }

    private fun applyStyle() {
        if (annotation.isEditable) {
            textField.style = ""
        } else {
            // set to read only
            textField.style = "-fx-background-color: rgba(200, 200, 200, 0.3);\n" +
                    "-fx-border-color: rgba(200, 200, 200, 1.0);\n" +
                    "-fx-border-width: 1px;"
        }
    }
}