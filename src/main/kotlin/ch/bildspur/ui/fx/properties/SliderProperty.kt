package ch.bildspur.ui.fx.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.fx.utils.JavaFXUtils
import ch.bildspur.ui.properties.SliderParameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import ch.bildspur.ui.fx.ResettableFXFieldProperty
import ch.bildspur.util.format
import javafx.application.Platform
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class SliderProperty(field: Field, obj: Any, val annotation: SliderParameter)
    : ResettableFXFieldProperty<Number>(field, obj) {

    private val slider = Slider(annotation.minValue, annotation.maxValue, 0.0)
    private val valueLabel = Label()

    init {
        val model = field.get(obj) as DataModel<Number>
        initialValue = model.value

        slider.majorTickUnit = if (model.value is Int || model.value is Long) 1.0 else annotation.majorTick
        slider.minorTickCount = 0
        slider.isSnapToTicks = if (model.value is Int || model.value is Long) true else annotation.snap

        slider.maxWidth = Double.MAX_VALUE
        setHgrow(slider, Priority.ALWAYS)

        val digits = if (model.value is Int || annotation.roundInt) 0 else 2

        val box = HBox(slider, valueLabel)
        box.spacing = 10.0
        setHgrow(box, Priority.ALWAYS)
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
                JavaFXUtils.setDoubleToNumberModel(slider.value, model)

                preventFirstTime {
                    propertyChanged(this)
                }
            }
        }

        slider.setOnMouseClicked {
            if (it.clickCount == 2) {
                model.value = initialValue
            }
        }
    }
}