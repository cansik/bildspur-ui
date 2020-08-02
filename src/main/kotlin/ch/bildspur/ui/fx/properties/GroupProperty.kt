package ch.bildspur.ui.fx.properties

import ch.bildspur.ui.properties.GroupParameter
import ch.bildspur.ui.fx.PropertiesControl
import ch.bildspur.ui.fx.BaseFXFieldProperty
import ch.bildspur.ui.properties.PropertyComponent
import javafx.scene.control.TitledPane
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import java.lang.Double
import java.lang.reflect.Field

class GroupProperty (field : Field, obj: Any, private val annoation: GroupParameter) : BaseFXFieldProperty(field, obj), PropertyComponent {
    private val control = PropertiesControl()
    private val titledPane = TitledPane(annoation.name, control)

    init {
        titledPane.isExpanded = annoation.expanded
        titledPane.isCollapsible = annoation.collapsible
        titledPane.font = Font.font("Helvetica", FontWeight.BOLD, 14.0)
        setHgrow(titledPane, Priority.ALWAYS)

        children.add(titledPane)

        control.propertyChanged += {
            propertyChanged.invoke(this@GroupProperty)
        }

        val subObject = field.get(obj)
        control.initView(subObject)
    }
}