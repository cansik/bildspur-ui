package ch.bildspur.ui.fx

import ch.bildspur.ui.fx.BaseFXProperty
import java.lang.reflect.Field

abstract class ResettableFXFieldProperty<T : Any>(field: Field, obj: Any)
    : BaseFXFieldProperty(field, obj) {
    protected lateinit var initialValue : T
}