package ch.bildspur.ui.html

import ch.bildspur.event.Event
import ch.bildspur.ui.properties.BaseProperty

open class BaseHTMLProperty : BaseProperty {
    override val propertyChanged = Event<BaseProperty>()

    @Volatile override var isSetup = false

    override fun preventFirstTime(block : () -> Unit) {
        if(isSetup)
            block()

        isSetup = true
    }
}