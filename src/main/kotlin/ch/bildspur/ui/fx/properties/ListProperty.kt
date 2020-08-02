package ch.bildspur.ui.fx.properties

import ch.bildspur.model.ListDataModel
import ch.bildspur.ui.properties.ListParameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ListView
import javafx.scene.layout.Priority
import java.lang.reflect.Field

class ListProperty (field: Field, obj: Any, annoation: ListParameter) : BaseFXFieldProperty(field, obj) {
    private val listView = ListView<String>()
    private val data : ObservableList<String> = FXCollections.observableArrayList()

    init {
        val model = field.get(obj) as ListDataModel<Any>
        listView.prefHeight = annoation.height
        listView.items = data

        model.onChanged += {
            data.setAll(model.map { e -> e.toString() })
        }
        model.fireLatest()

        setHgrow(listView, Priority.ALWAYS)
        children.add(listView)
    }
}