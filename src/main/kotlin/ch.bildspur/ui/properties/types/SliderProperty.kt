package ch.bildspur.ui.properties.types

import ch.bildspur.model.DataModel
import ch.bildspur.ui.PropertyUtils
import ch.bildspur.ui.properties.SliderParameter
import ch.bildspur.util.format
import javafx.application.Platform
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.HBox
import java.lang.reflect.Field
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@Suppress("UNCHECKED_CAST")
class SliderProperty(field: Field, obj: Any, val annotation: SliderParameter) : BaseFieldProperty(field, obj) {
    private val slider = Slider(annotation.minValue, annotation.maxValue, 0.0)
    private val valueLabel = Label()

    init {
        val model = field.get(obj) as DataModel<Number>

        slider.majorTickUnit = if (model.value is Int || model.value is Long) 1.0 else annotation.majorTick
        slider.minorTickCount = 0
        slider.isSnapToTicks = if (model.value is Int || model.value is Long) true else annotation.snap

        val digits = if (model.value is Int || annotation.roundInt) 0 else 2

        val box = HBox(slider, valueLabel)
        box.spacing = 10.0
        children.add(box)

        model.onChanged += {
            Platform.runLater {
                slider.value = model.value.toDouble()

                if (model.value is Int || model.value is Long)
                    valueLabel.text = model.value.toString()
                else
                    valueLabel.text = model.value.format(digits)
            }
        }
        model.fireLatest()

        slider.valueProperty().addListener { _, _, _ ->
            run {
                PropertyUtils.setDoubleToNumberModel(slider.value, model)

                preventFirstTime {
                    propertyChanged(this)
                }
            }
        }
    }
}