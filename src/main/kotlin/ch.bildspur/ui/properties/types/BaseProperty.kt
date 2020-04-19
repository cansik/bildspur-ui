package ch.bildspur.ui.properties.types

import ch.bildspur.event.Event
import javafx.scene.layout.Pane
import java.lang.reflect.Field

open class BaseProperty : Pane() {
    val propertyChanged = Event<BaseProperty>()

    @Volatile protected var isSetup = false
        private set

    protected fun preventFirstTime(block : () -> Unit) {
        if(isSetup)
            block()

        isSetup = true
    }
}