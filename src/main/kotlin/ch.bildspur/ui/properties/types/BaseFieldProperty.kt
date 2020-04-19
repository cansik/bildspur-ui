package ch.bildspur.ui.properties.types

import java.lang.reflect.Field

abstract class BaseFieldProperty(val field: Field, val obj: Any) : BaseProperty() {}