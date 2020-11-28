package ch.bildspur.ui.fx.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.fx.utils.JavaFXUtils
import ch.bildspur.ui.properties.SliderParameter
import ch.bildspur.ui.fx.ResettableFXFieldProperty
import ch.bildspur.util.Mapping
import ch.bildspur.util.format
import javafx.application.Platform
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import java.lang.reflect.Field
import kotlin.math.roundToInt
import kotlin.math.roundToLong

@Suppress("UNCHECKED_CAST")
class SliderProperty(field: Field, obj: Any, val annotation: SliderParameter)
    : ResettableFXFieldProperty<Number>(field, obj) {

    private val slider = Slider(0.0, 1.0, 0.0)
    private val valueLabel = Label()

    private val model = field.get(obj) as DataModel<Number>
    @Volatile private var silent = false

    init {
        initialValue = model.value

        // only add ticks if is linear
        if(annotation.mapping == Mapping.Linear) {
            slider.majorTickUnit = if (model.value is Int || model.value is Long) 1.0 else annotation.majorTick
            slider.minorTickCount = 0
            slider.isSnapToTicks = if (model.value is Int || model.value is Long) true else annotation.snap
        } else {
            slider.isSnapToTicks = false
        }

        slider.maxWidth = Double.MAX_VALUE
        setHgrow(slider, Priority.ALWAYS)

        val digits = if (model.value is Int || annotation.roundInt) 0 else 2

        val box = HBox(slider, valueLabel)
        box.spacing = 10.0
        setHgrow(box, Priority.ALWAYS)
        children.add(box)

        model.onChanged += {
            val value = model.value.toDouble().mapIn()

            //Platform.runLater {
                silent = true
                slider.value = value

                if (model.value is Int || model.value is Long)
                    valueLabel.text = value.roundToLong().toString()
                else
                    valueLabel.text = value.format(digits)
                silent = false
           // }
        }
        model.fireLatest()

        slider.valueProperty().addListener { _, _, _ ->
            run {
                if (!silent) {
                    JavaFXUtils.setDoubleToNumberModel(slider.value.mapOut(), model)

                    preventFirstTime {
                        propertyChanged(this)
                    }
                } else {
                    println("not updating model")
                }
            }
        }

        slider.setOnMouseClicked {
            if (it.clickCount == 2) {
                model.value = initialValue
            }
        }
    }

    /**
     * From outside linear range to eased range.
     */
    private fun Double.mapIn() : Double {
        val norm = (this - annotation.minValue) / (annotation.maxValue - annotation.minValue)
        return annotation.mapping.mapping(norm)
    }

    /**
     * From eased range to linear outside
     */
    private fun Double.mapOut() : Double {
        val mapped = annotation.mapping.mapping(this)
        return mapped * (annotation.maxValue - annotation.minValue) + annotation.minValue
    }
}