package ch.bildspur.ui.fx.properties

import ch.bildspur.math.Float2
import ch.bildspur.model.DataModel
import ch.bildspur.ui.fx.controls.NumberField
import ch.bildspur.ui.properties.Float2Parameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.control.Label
import javafx.scene.control.TextFormatter
import javafx.scene.input.MouseButton
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.util.converter.FloatStringConverter
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class Float2Property(field: Field, obj: Any, val annotation: Float2Parameter) : BaseFXFieldProperty(field, obj) {

    val model = field.get(obj) as DataModel<Float2>
    val xField = NumberField<Float>(TextFormatter(FloatStringConverter()))
    val yField = NumberField<Float>(TextFormatter(FloatStringConverter()))

    val fields = mapOf(
            Pair("X", xField),
            Pair("Y", yField))

    private var lastDraggedValue = 0.0

    init {
        val vb = VBox()
        vb.spacing = 5.0
        vb.maxWidth = Double.MAX_VALUE
        setHgrow(vb, Priority.ALWAYS)

        // setup fields
        fields.forEach {
            val label = Label("${it.key}:")
            label.cursor = Cursor.H_RESIZE

            label.setOnMouseDragged { event ->
                var value = (lastDraggedValue - event.x) * -1.0
                lastDraggedValue = event.x

                // slow down
                if(event.button == MouseButton.PRIMARY) {
                    value /= 100.0
                }

                if(event.button == MouseButton.SECONDARY) {
                    value /= 10.0
                }

                it.value.value += value
                it.value.fireEvent(ActionEvent())
            }
            label.setOnMouseReleased {
                lastDraggedValue = 0.0
            }

            it.value.maxWidth = Double.MAX_VALUE
            setHgrow(it.value, Priority.ALWAYS)
            label.prefWidth = 20.0

            it.value.setOnAction {
                model.value = Float2(
                        xField.value.toFloat(),
                        yField.value.toFloat())
            }

            val hb = HBox(label, it.value)
            hb.alignment = Pos.CENTER_LEFT
            vb.children.add(hb)
        }

        // setup binding
        model.onChanged += {
            Platform.runLater {
                xField.value = model.value.x.toDouble()
                yField.value = model.value.y.toDouble()
            }
        }
        model.fireLatest()
        children.add(vb)
    }
}