package ch.bildspur.ui.fx.properties

import ch.bildspur.color.RGB
import ch.bildspur.model.DataModel
import ch.bildspur.ui.properties.ColorParameter
import ch.bildspur.ui.fx.utils.toFXColor
import ch.bildspur.ui.fx.utils.toRGB
import ch.bildspur.ui.fx.BaseFXFieldProperty
import javafx.application.Platform
import javafx.scene.control.ColorPicker
import javafx.scene.layout.Priority
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class ColorProperty(field: Field, obj: Any, val annotation: ColorParameter) : BaseFXFieldProperty(field, obj) {

    private val colorPicker = ColorPicker()

    init {
        colorPicker.maxWidth = Double.MAX_VALUE
        setHgrow(colorPicker, Priority.ALWAYS)
        children.add(colorPicker)

        val model = field.get(obj) as DataModel<RGB>
        model.onChanged += {
            Platform.runLater {
                colorPicker.value = model.value.toFXColor()
            }
        }
        model.fireLatest()

        colorPicker.valueProperty().addListener { _, _, value ->
            model.value = value.toRGB()
            propertyChanged(this)
        }
    }
}