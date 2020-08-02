package ch.bildspur.ui.properties

import java.lang.reflect.Field

@Suppress("UNCHECKED_CAST")
class PropertyReader(val properties : MutableList<PropertiesRegistryEntry<*>> = mutableListOf()) {
    private fun readPropertyFields(obj: Any): List<Field> {
        val c = obj.javaClass

        val fields = c.declaredFields.filter {
            for(property in properties) {
                property as PropertiesRegistryEntry<Annotation>
                if (it.isAnnotationPresent(property.annotation!!)) {
                    return@filter true
                }
            }
            return@filter false
        }
        fields.forEach { it.isAccessible = true }
        return fields
    }

    fun readPropertyAnnotations(obj: Any) : List<PropertyField> {
        return readPropertyFields(obj).map {
            // find annotation field
            it to properties.find { p -> it.isAnnotationPresent(p.annotation!!) }!! as PropertiesRegistryEntry<Annotation>
        }.map { (field, property) ->
            val annotation = field.getAnnotation(property.annotation!!)!!
            PropertyField(annotation,
                    property.getName(annotation),
                    property.getPropertyControl(field, obj, annotation))
        }
    }
}