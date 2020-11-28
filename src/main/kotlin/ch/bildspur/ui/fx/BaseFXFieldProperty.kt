package ch.bildspur.ui.fx

import java.lang.reflect.Field

abstract class BaseFXFieldProperty(val field: Field, val obj: Any) : BaseFXProperty() {}