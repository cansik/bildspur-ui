package ch.bildspur.ui.fx.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.properties.BooleanParameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import javafx.application.Platform
import javafx.scene.control.CheckBox
import javafx.scene.layout.Priority
import java.lang.Double
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class BooleanProperty(field: Field, obj: Any, val annoation: BooleanParameter) : BaseFXFieldProperty(field, obj) {

    val checkBox = CheckBox()

    init {
        setHgrow(checkBox, Priority.ALWAYS)
        children.add(checkBox)

        val model = field.get(obj) as DataModel<Boolean>
        model.onChanged += {
            Platform.runLater {
                checkBox.isSelected = model.value
            }
        }
        model.fireLatest()

        checkBox.setOnAction {
            model.value = checkBox.isSelected
            propertyChanged(this)
        }
    }
}