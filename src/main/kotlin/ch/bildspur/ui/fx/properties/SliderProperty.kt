package ch.bildspur.ui.fx.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.fx.ResettableFXFieldProperty
import ch.bildspur.ui.fx.styleValueLabel
import ch.bildspur.ui.fx.utils.JavaFXUtils
import ch.bildspur.ui.properties.SliderParameter
import ch.bildspur.util.Mapping
import ch.bildspur.util.format
import javafx.application.Platform
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import java.lang.reflect.Field
import kotlin.math.max
import kotlin.math.min

@Suppress("UNCHECKED_CAST")
class SliderProperty(field: Field, obj: Any, val annotation: SliderParameter)
    : ResettableFXFieldProperty<Number>(field, obj) {

    private val slider = Slider(annotation.minValue, annotation.maxValue, 0.0)
    private val valueLabel = Label()

    private val model = field.get(obj) as DataModel<Number>

    private val valueRange = annotation.maxValue - annotation.minValue

    // used for internal silencing
    @Volatile
    private var silent = false

    init {
        initialValue = model.value

        // only add ticks if is linear
        if (annotation.mapping == Mapping.Linear) {
            slider.majorTickUnit = if (model.value is Int || model.value is Long) 1.0 else annotation.majorTick
            slider.minorTickCount = 0
            slider.isSnapToTicks = if (model.value is Int || model.value is Long) true else annotation.snap
        } else {
            slider.isSnapToTicks = false
        }

        valueLabel.styleValueLabel()

        slider.maxWidth = Double.MAX_VALUE
        setHgrow(slider, Priority.ALWAYS)

        val digits = if (model.value is Int || annotation.roundInt) 0 else annotation.labelDigits

        val box = HBox(slider, valueLabel)
        box.spacing = 10.0
        setHgrow(box, Priority.ALWAYS)
        children.add(box)

        model.onChanged += {
            Platform.runLater {
                silent = true
                slider.value = model.value.toDouble().mapIn()

                // todo: find better way to format number (more intelligent to show relevant digits)
                if (model.value is Int || model.value is Long)
                    valueLabel.text = model.value.toString()
                else
                    valueLabel.text = model.value.format(digits)
                silent = false
            }
        }
        model.fireLatest()

        slider.valueProperty().addListener { _, _, _ ->
            run {
                if (!silent) {
                    JavaFXUtils.setDoubleToNumberModel(slider.value.mapOut(), model)
                }

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

    /**
     * From outside linear range to eased range.
     */
    private fun Double.mapIn(): Double {
        val norm = (this - annotation.minValue) / valueRange
        return annotation.mapping.inverseMapping(norm).constrainToRange() * valueRange + annotation.minValue
    }

    /**
     * From eased range to linear outside
     */
    private fun Double.mapOut(): Double {
        val mapped = annotation.mapping.mapping((this - annotation.minValue) / valueRange).constrainToRange()
        return (mapped * valueRange) + annotation.minValue
    }

    private fun Double.constrainToRange(): Double {
        return min(max(0.0, this), 1.0)
    }
}