package ch.bildspur.ui.properties

import java.lang.reflect.Field

data class PropertyField (val annotation: Annotation, val name : String, val property : BaseProperty, val field : Field)