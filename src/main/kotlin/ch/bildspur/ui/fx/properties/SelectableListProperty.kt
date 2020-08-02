package ch.bildspur.ui.fx.properties

import ch.bildspur.model.SelectableDataModel
import ch.bildspur.ui.properties.SelectableListParameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ComboBox
import java.lang.reflect.Field


class SelectableListProperty (field: Field, obj: Any, annoation: SelectableListParameter) : BaseFXFieldProperty(field, obj) {
    private val box = ComboBox<String>()
    private val data : ObservableList<String> = FXCollections.observableArrayList()
    private var preventUpdate = false

    init {
        val model = field.get(obj) as SelectableDataModel<Any>
        box.items = data
        model.onChanged += {
            if(!preventUpdate) {
                data.setAll(model.map { e -> e.toString() })
                box.selectionModel.select(model.selectedIndex)
            }
        }
        model.fireLatest()

        box.setOnAction {
            preventUpdate = true
            model.selectedIndex = box.selectionModel.selectedIndex
            preventUpdate = false

            preventFirstTime {
                propertyChanged(this)
            }
        }

        box.prefWidth = 170.0
        children.add(box)
    }
}