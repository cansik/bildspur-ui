package ch.bildspur.ui.fx.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.fx.controls.NumberField
import ch.bildspur.ui.fx.utils.JavaFXUtils
import ch.bildspur.ui.properties.NumberParameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.TextFormatter
import javafx.scene.layout.HBox
import javafx.scene.text.Font
import javafx.util.converter.NumberStringConverter
import java.lang.reflect.Field
import java.text.NumberFormat
import java.util.*

class NumberProperty(field: Field, obj: Any, val annotation: NumberParameter) : BaseFXFieldProperty(field, obj) {

    val format = NumberFormat.getInstance(Locale.ENGLISH)
    val numberStringConverter = NumberStringConverter(format)
    val textFormatter = TextFormatter(numberStringConverter)

    val numberField = NumberField<Number>(textFormatter)
    val unitField = Label(annotation.unit)

    val box = HBox(numberField, unitField)

    init {
        format.isGroupingUsed = false
        unitField.font = Font("Helvetica", 10.0)

        box.spacing = 10.0
        box.alignment = Pos.CENTER_LEFT
        children.add(box)

        val model = field.get(obj) as DataModel<Number>
        model.onChanged += {
            Platform.runLater {
                numberField.value = model.value.toDouble()
            }
        }
        model.fireLatest()

        numberField.setOnAction {
            JavaFXUtils.setDoubleToNumberModel(numberField.value, model)
            propertyChanged(this)
        }
    }
}