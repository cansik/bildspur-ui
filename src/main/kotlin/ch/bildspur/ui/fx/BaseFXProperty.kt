package ch.bildspur.ui.fx

import ch.bildspur.event.Event
import ch.bildspur.ui.properties.BaseProperty
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane

open class BaseFXProperty : HBox(), BaseProperty {
    override val propertyChanged = Event<BaseProperty>()

    @Volatile override var isSetup = false

    override fun preventFirstTime(block : () -> Unit) {
        if(isSetup)
            block()

        isSetup = true
    }
}