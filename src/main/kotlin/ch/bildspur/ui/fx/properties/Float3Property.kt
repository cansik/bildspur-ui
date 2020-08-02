package ch.bildspur.ui.fx.properties

import ch.bildspur.math.Float3
import ch.bildspur.model.DataModel
import ch.bildspur.ui.fx.controls.NumberField
import ch.bildspur.ui.properties.Float3Parameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import javafx.application.Platform
import javafx.scene.control.Label
import javafx.scene.control.TextFormatter
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.util.converter.FloatStringConverter
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class Float3Property(field: Field, obj: Any, val annotation: Float3Parameter) : BaseFXFieldProperty(field, obj) {

    val model = field.get(obj) as DataModel<Float3>
    val xField = NumberField<Float>(TextFormatter(FloatStringConverter()))
    val yField = NumberField<Float>(TextFormatter(FloatStringConverter()))
    val zField = NumberField<Float>(TextFormatter(FloatStringConverter()))

    val fields = mapOf(
            Pair("X", xField),
            Pair("Y", yField),
            Pair("Z", zField))

    init {
        val box = VBox()
        box.spacing = 5.0

        // setup fields
        fields.forEach {
            val label = Label("${it.key}:")

            it.value.prefWidth = 160.0
            label.prefWidth = 20.0

            it.value.setOnAction {
                model.value = Float3(
                        xField.value.toFloat(),
                        yField.value.toFloat(),
                        zField.value.toFloat())
            }

            box.children.add(HBox(label, it.value))
        }

        // setup binding
        model.onChanged += {
            Platform.runLater {
                xField.value = model.value.x.toDouble()
                yField.value = model.value.y.toDouble()
                zField.value = model.value.z.toDouble()
            }
        }
        model.fireLatest()
        children.add(box)
    }
}