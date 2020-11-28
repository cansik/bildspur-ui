package ch.bildspur.ui.fx

import ch.bildspur.event.Event
import ch.bildspur.ui.properties.ParameterInformation
import ch.bildspur.ui.properties.PropertyComponent
import ch.bildspur.ui.properties.PropertyReader
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.Font


class PropertiesControl(val reader: PropertyReader = PropertyReader(FXPropertyRegistry.properties)) : VBox() {
    val propertyChanged = Event<BaseFXProperty>()

    init {
        spacing = 10.0
        alignment = Pos.TOP_CENTER
        padding = Insets(10.0, 10.0, 10.0, 10.0)
    }

    fun initView(obj: Any) {
        clearView()

        // create view
        reader.readPropertyAnnotations(obj).forEach {
            if(it.property is BaseFXProperty) {
                // component exception
                if (it.property is PropertyComponent) {
                    addComponent(it.property)
                    return@forEach
                }

                // find help parameter if available
                val parameterHelp = it.field.getAnnotation(ParameterInformation::class.java)
                addProperty(it.name, it.property, parameterHelp)
                return@forEach
            }
        }
    }

    fun clearView() {
        this.children.clear()
    }

    private fun addComponent(propertyView: BaseFXProperty) {
        propertyView.propertyChanged += {
            propertyChanged(propertyView)
        }

        children.add(propertyView)
    }

    private fun addProperty(name: String, propertyView: BaseFXProperty, parameterInformation: ParameterInformation?) {
        propertyView.propertyChanged += {
            propertyChanged(propertyView)
        }

        val nameLabel = Label("$name:")
        nameLabel.prefWidth = 80.0
        nameLabel.font = Font("Helvetica", 12.0)
        nameLabel.isWrapText = true

        if(parameterInformation != null) {
            val t = Tooltip(parameterInformation.helpText)
            nameLabel.tooltip = t
        }

        HBox.setHgrow(propertyView, Priority.ALWAYS)
        HBox.setHgrow(nameLabel, Priority.ALWAYS)

        val box = HBox(nameLabel, propertyView)
        box.spacing = 10.0
        box.prefHeight = propertyView.prefHeight
        box.alignment = Pos.CENTER_LEFT

        children.add(box)
    }
}