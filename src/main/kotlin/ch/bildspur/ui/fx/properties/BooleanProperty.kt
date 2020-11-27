package ch.bildspur.ui.fx.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.fx.BaseFXFieldProperty
import ch.bildspur.ui.properties.BooleanParameter
import javafx.application.Platform
import javafx.beans.property.BooleanProperty
import javafx.geometry.Insets
import javafx.scene.control.ButtonBase
import javafx.scene.control.CheckBox
import javafx.scene.control.Control
import javafx.scene.control.ToggleButton
import javafx.scene.layout.Priority
import org.controlsfx.control.ToggleSwitch
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class BooleanProperty(field: Field, obj: Any, annotation: BooleanParameter) : BaseFXFieldProperty(field, obj) {
    class SelectableComponent(val element: Control, val selectedProperty: BooleanProperty)

    private val selectableComponent = if (annotation.useToggleSwitch) {
        val switch = ToggleSwitch()
        switch.padding = Insets.EMPTY
        SelectableComponent(switch, switch.selectedProperty())
    } else {
        val checkbox = CheckBox()
        setHgrow(checkbox, Priority.ALWAYS)
        SelectableComponent(checkbox, checkbox.selectedProperty())
    }

    init {
        children.add(selectableComponent.element)

        val model = field.get(obj) as DataModel<Boolean>
        model.onChanged += {
            Platform.runLater {
                selectableComponent.selectedProperty.value = model.value
            }
        }
        model.fireLatest()

        selectableComponent.selectedProperty.addListener { _, _, value ->
            model.value = value
            propertyChanged(this)
        }
    }
}