package ch.bildspur.ui.properties.types

import ch.bildspur.model.DataModel
import ch.bildspur.ui.properties.GroupParameter
import ch.bildspur.ui.properties.PropertiesControl
import java.lang.reflect.Field

class GroupProperty (field : Field, obj: Any, private val annoation: GroupParameter) : BaseFieldProperty(field, obj), PropertyComponent {
    private val control = PropertiesControl()

    init {
        // todo: add collapsable container with titel

        control.propertyChanged += {
            propertyChanged.invoke(this@GroupProperty)
        }

        val subObject = field.get(obj)
        control.initView(subObject)
        children.add(control)
    }
}