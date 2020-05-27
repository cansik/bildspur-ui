package ch.bildspur.ui.properties.types

import ch.bildspur.model.DataModel
import ch.bildspur.ui.properties.GroupParameter
import ch.bildspur.ui.properties.PropertiesControl
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.TitledPane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.lang.reflect.Field

class GroupProperty (field : Field, obj: Any, private val annoation: GroupParameter) : BaseFieldProperty(field, obj), PropertyComponent {
    private val control = PropertiesControl()
    private val titledPane = TitledPane(annoation.name, control)

    init {
        titledPane.isExpanded = annoation.expanded
        titledPane.isCollapsible = annoation.collapsible
        titledPane.font = Font.font("Helvetica", FontWeight.BOLD, 14.0)
        children.add(titledPane)

        control.propertyChanged += {
            propertyChanged.invoke(this@GroupProperty)
        }

        val subObject = field.get(obj)
        control.initView(subObject)
    }
}