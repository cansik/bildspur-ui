package ch.bildspur.ui.properties

import ch.bildspur.event.Event

interface BaseProperty {
    val propertyChanged : Event<BaseProperty>
    var isSetup : Boolean
    fun preventFirstTime(block : () -> Unit)
}