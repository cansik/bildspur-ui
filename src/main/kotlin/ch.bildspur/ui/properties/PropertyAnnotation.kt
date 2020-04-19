package ch.bildspur.ui.properties

import ch.bildspur.ui.properties.types.*

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class StringParameter(val name: String, val isEditable: Boolean = true)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class TextParameter(val name: String, val isEditable: Boolean = true,
                               val width : Double = 200.0, val height : Double = 120.0,
                               val wordWrap : Boolean = false)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SliderParameter(val name: String,
                                 val minValue: Double = 0.0,
                                 val maxValue: Double = 100.0,
                                 val majorTick: Double = 1.0,
                                 val snap: Boolean = true,
                                 val roundInt: Boolean = false)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class RangeSliderParameter(val name: String,
                                      val minValue: Double = 0.0,
                                      val maxValue: Double = 100.0,
                                      val majorTick: Double = 1.0,
                                      val snap: Boolean = true,
                                      val roundInt: Boolean = false)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class NumberParameter(val name: String, val unit : String = "")

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class BooleanParameter(val name: String)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Float2Parameter(val name: String)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Float3Parameter(val name: String)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ActionParameter(val name: String, val caption: String, val invokesChange : Boolean = true)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnumParameter(val name: String)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class LabelParameter(val name: String)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class GroupParameter(val name: String)

object PropertyAnnotation {
    init {
        val properties = PropertiesRegistry.properties

        properties.add(PropertiesRegistryEntry(StringParameter::class.java, StringParameter::name, ::StringProperty))
        properties.add(PropertiesRegistryEntry(TextParameter::class.java, TextParameter::name, ::TextProperty))
        properties.add(PropertiesRegistryEntry(SliderParameter::class.java, SliderParameter::name, ::SliderProperty))
        properties.add(PropertiesRegistryEntry(RangeSliderParameter::class.java, RangeSliderParameter::name, ::RangeSliderProperty))
        properties.add(PropertiesRegistryEntry(NumberParameter::class.java, NumberParameter::name, ::NumberProperty))
        properties.add(PropertiesRegistryEntry(BooleanParameter::class.java, BooleanParameter::name, ::BooleanProperty))
        properties.add(PropertiesRegistryEntry(Float2Parameter::class.java, Float2Parameter::name, ::Float2Property))
        properties.add(PropertiesRegistryEntry(Float3Parameter::class.java, Float3Parameter::name, ::Float3Property))
        properties.add(PropertiesRegistryEntry(ActionParameter::class.java, ActionParameter::name, ::ActionProperty))
        properties.add(PropertiesRegistryEntry(EnumParameter::class.java, EnumParameter::name, ::EnumProperty))

        properties.add(PropertiesRegistryEntry(LabelParameter::class.java, LabelParameter::name, ::LabelProperty))
        properties.add(PropertiesRegistryEntry(GroupParameter::class.java, GroupParameter::name, ::GroupProperty))
    }
}