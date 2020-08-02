package ch.bildspur.ui.html

import ch.bildspur.ui.html.properties.ActionProperty
import ch.bildspur.ui.html.properties.StringProperty
import ch.bildspur.ui.properties.ActionParameter
import ch.bildspur.ui.properties.PropertiesRegistryEntry
import ch.bildspur.ui.properties.StringParameter

object HTMLPropertyRegistry {
    val properties = mutableListOf<PropertiesRegistryEntry<*>>()

    init {
        properties.add(PropertiesRegistryEntry(StringParameter::class.java, StringParameter::name, ::StringProperty))
        properties.add(PropertiesRegistryEntry(ActionParameter::class.java, ActionParameter::name, ::ActionProperty))
    }
}