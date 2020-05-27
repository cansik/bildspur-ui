package ch.bildspur.ui.fx

import ch.bildspur.model.DataModel
import kotlin.math.roundToInt
import kotlin.math.roundToLong

object JavaFXUtils {
    fun setDoubleToNumberModel(value : Double, model : DataModel<Number>) {
        if(model.value is Byte)
            model.value = value.toByte()

        if (model.value is Short)
            model.value = value.toShort()

        if (model.value is Int)
            model.value = value.roundToInt()

        if (model.value is Long)
            model.value = value.roundToLong()

        if (model.value is Float)
            model.value = value.toFloat()

        if (model.value is Double)
            model.value = value
    }
}