package ch.bildspur.ui.fx.properties

import ch.bildspur.ui.properties.LabelParameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import ch.bildspur.ui.properties.PropertyComponent
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.lang.reflect.Field

class LabelProperty (field: Field, obj: Any, annoation: LabelParameter) : BaseFXFieldProperty(field, obj), PropertyComponent {
    private val label = Label(annoation.name)

    init {
        label.padding = Insets(10.0, 0.0, 0.0, 0.0)
        label.font = Font.font("Helvetica", FontWeight.BOLD, annoation.fontSize)
        children.add(label)
    }
}