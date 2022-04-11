package ch.bildspur.ui.properties

import java.lang.reflect.Field
import java.util.*


@Suppress("UNCHECKED_CAST")
class PropertyReader(val properties : MutableList<PropertiesRegistryEntry<*>> = mutableListOf()) {
    private fun readPropertyFields(obj: Any): List<Field> {
        val c = obj.javaClass

        val fields = getDeclaredFields(c).filterNotNull().filter {
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

    private fun getDeclaredFields(clazz: Class<*>): Array<Field?> {
        val fields = clazz.declaredFields
        return if (clazz.superclass != Any::class.java) {
            val pFields = getDeclaredFields(clazz.superclass)
            val allFields = arrayOfNulls<Field>(fields.size + pFields.size)
            Arrays.setAll(allFields) { i: Int -> if (i < pFields.size) pFields[i] else fields[i - pFields.size] }
            allFields
        } else fields
    }

    fun readPropertyAnnotations(obj: Any) : List<PropertyField> {
        return readPropertyFields(obj).map {
            // find annotation field
            it to properties.find { p -> it.isAnnotationPresent(p.annotation!!) }!! as PropertiesRegistryEntry<Annotation>
        }.map { (field, property) ->
            val annotation = field.getAnnotation(property.annotation!!)!!
            PropertyField(annotation,
                    property.getName(annotation),
                    property.getPropertyControl(field, obj, annotation),
                    field)
        }
    }
}