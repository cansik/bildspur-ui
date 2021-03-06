package ch.bildspur.ui.fx.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.properties.ProgressParameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import ch.bildspur.ui.fx.styleValueLabel
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.util.converter.NumberStringConverter
import java.lang.reflect.Field
import java.text.NumberFormat
import java.util.*
import kotlin.math.round

@Suppress("UNCHECKED_CAST")
class ProgressProperty(field: Field, obj: Any, val annotation: ProgressParameter) : BaseFXFieldProperty(field, obj) {

    private val format = NumberFormat.getInstance(Locale.ENGLISH)
    private val numberStringConverter = NumberStringConverter(format)

    private val progressBar = ProgressBar()
    private val valueField = Label()

    private val box = HBox(progressBar, valueField)

    init {
        format.isGroupingUsed = false
        valueField.styleValueLabel()

        box.spacing = 10.0
        box.alignment = Pos.CENTER_LEFT

        progressBar.maxWidth = Double.MAX_VALUE
        setHgrow(progressBar, Priority.ALWAYS)
        setHgrow(box, Priority.ALWAYS)

        children.add(box)

        valueField.isVisible = annotation.displayValue

        val model = field.get(obj) as DataModel<Number>
        model.onChanged += {
            Platform.runLater {
                progressBar.progress = model.value.toDouble()
                valueField.text = "${numberStringConverter.toString(round(model.value.toDouble() * 100.0))}%"
            }
        }
        model.fireLatest()
    }
}