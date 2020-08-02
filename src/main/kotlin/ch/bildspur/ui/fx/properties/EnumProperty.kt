package ch.bildspur.ui.fx.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.properties.EnumParameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import javafx.application.Platform
import javafx.scene.control.ComboBox
import javafx.scene.layout.Priority
import java.lang.Double.MAX_VALUE
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class EnumProperty(field: Field, obj: Any, val annotation: EnumParameter) : BaseFXFieldProperty(field, obj) {
    val box = ComboBox<Enum<*>>()

    init {
        // extract enum values
        val model = field.get(obj) as DataModel<Enum<*>>

        val enumObj = model.value
        val classEnum = enumObj::javaClass.get()

        if (classEnum.isEnum) {
            classEnum.enumConstants.forEach {
                box.items.add(it)
            }
        }

        // add binding
        model.onChanged += {
            Platform.runLater {
                box.selectionModel.select(model.value)
            }
        }
        model.fireLatest()

        box.setOnAction {
            model.value = box.selectionModel.selectedItem

            preventFirstTime {
                propertyChanged(this)
            }
        }

        box.maxWidth = MAX_VALUE
        setHgrow(box, Priority.ALWAYS)
        children.add(box)
    }
}