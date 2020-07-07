package ch.bildspur.ui.properties.types

import ch.bildspur.model.ListDataModel
import ch.bildspur.ui.properties.ListParameter
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ListView
import java.lang.reflect.Field

class ListProperty (field: Field, obj: Any, annoation: ListParameter) : BaseFieldProperty(field, obj) {
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
        children.add(listView)
    }
}