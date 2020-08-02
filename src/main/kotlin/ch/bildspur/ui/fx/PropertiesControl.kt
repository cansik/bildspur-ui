package ch.bildspur.ui.fx

import ch.bildspur.event.Event
import ch.bildspur.ui.properties.PropertiesRegistry
import ch.bildspur.ui.properties.PropertiesRegistryEntry
import ch.bildspur.ui.properties.PropertyAnnotation
import ch.bildspur.ui.properties.PropertyComponent
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class PropertiesControl : VBox() {
    val propertyChanged = Event<BaseFXProperty>()

    init {
        spacing = 10.0
        alignment = Pos.TOP_CENTER
        padding = Insets(10.0, 20.0, 10.0, 10.0)

        // register base annotations
        PropertyAnnotation
    }

    fun initView(obj: Any) {
        clearView()

        val params = readParameters(obj)

        // create view
        params.forEach {
            for(property in PropertiesRegistry.properties) {
                property as PropertiesRegistryEntry<Annotation>
                if(it.isAnnotationPresent(property.annotation)) {
                    val annotation = it.getAnnotation(property.annotation)!!
                    val name = property.getName(annotation)
                    val propertyControl = property.getPropertyControl(it, obj, annotation)

                    if(propertyControl is BaseFXProperty) {
                        // component exception
                        if (propertyControl is PropertyComponent) {
                            addComponent(propertyControl)
                            break
                        }

                        addProperty(name, propertyControl)
                        break
                    }
                }
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

    private fun addProperty(name: String, propertyView: BaseFXProperty) {
        propertyView.propertyChanged += {
            propertyChanged(propertyView)
        }

        val nameLabel = Label("$name:")
        nameLabel.prefWidth = 100.0
        nameLabel.font = Font("Helvetica", 12.0)
        nameLabel.isWrapText = true

        val box = HBox(nameLabel, propertyView)
        box.spacing = 10.0
        box.prefHeight = propertyView.prefHeight
        box.alignment = Pos.CENTER_LEFT
        children.add(box)
    }

    private fun readParameters(obj: Any): List<Field> {
        val c = obj.javaClass

        val fields = c.declaredFields.filter {
            for(property in PropertiesRegistry.properties) {
                property as PropertiesRegistryEntry<Annotation>
                if (it.isAnnotationPresent(property.annotation)) {
                    return@filter true
                }
            }
            return@filter false
        }
        fields.forEach { it.isAccessible = true }
        return fields
    }
}