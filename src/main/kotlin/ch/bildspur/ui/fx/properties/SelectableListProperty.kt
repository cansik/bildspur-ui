package ch.bildspur.ui.fx.properties

import ch.bildspur.model.SelectableDataModel
import ch.bildspur.ui.properties.SelectableListParameter
import ch.bildspur.ui.fx.BaseFXFieldProperty
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.ComboBox
import javafx.scene.layout.Priority
import java.lang.reflect.Field


class SelectableListProperty(field: Field, obj: Any, annotation: SelectableListParameter) :
    BaseFXFieldProperty(field, obj) {
    private val box = ComboBox<String>()
    private val data: ObservableList<String> = FXCollections.observableArrayList()
    private var preventUpdate = false
    private var preventUIUpdate = false

    init {
        val instance = this
        val model = field.get(obj) as SelectableDataModel<Any>
        box.items = data
        model.onChanged += {
            if (!preventUpdate) {
                Platform.runLater {
                    preventUIUpdate = true
                    data.setAll(model.map { e -> e.toString() })

                    box.isDisable = data.size == 0

                    if (model.selectedIndex < 0 && data.size != 0) {
                        model.selectedIndex = 0
                    }
                    box.selectionModel.select(model.selectedIndex)
                    preventUIUpdate = false

                    preventFirstTime {
                        propertyChanged(instance)
                    }
                }
            }
        }
        model.fireLatest()

        box.setOnAction {
            if (!preventUIUpdate) {
                if (model.selectedIndex == box.selectionModel.selectedIndex) {
                    return@setOnAction
                }

                preventUpdate = true
                model.selectedIndex = box.selectionModel.selectedIndex
                preventUpdate = false

                preventFirstTime {
                    propertyChanged(this)
                }
            }
        }

        box.maxWidth = Double.MAX_VALUE
        setHgrow(box, Priority.ALWAYS)
        children.add(box)
    }
}