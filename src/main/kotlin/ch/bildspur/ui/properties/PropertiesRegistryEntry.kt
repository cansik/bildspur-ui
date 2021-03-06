package ch.bildspur.ui.properties

import java.lang.reflect.Field

data class PropertiesRegistryEntry<T : Annotation>(val annotation : Class<out Annotation?>?,
                                                   val getName : (annotation: T) -> String,
                                                   val getPropertyControl : (field: Field, obj: Any, annotation: T) -> BaseProperty)