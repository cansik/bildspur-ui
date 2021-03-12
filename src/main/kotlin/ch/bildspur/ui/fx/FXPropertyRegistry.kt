package ch.bildspur.ui.fx

import ch.bildspur.ui.fx.properties.*
import ch.bildspur.ui.properties.*

object FXPropertyRegistry {
    val properties = mutableListOf<PropertiesRegistryEntry<*>>()

    init {
        properties.add(PropertiesRegistryEntry(StringParameter::class.java, StringParameter::name, ::StringProperty))
        properties.add(PropertiesRegistryEntry(TextParameter::class.java, TextParameter::name, ::TextProperty))
        properties.add(PropertiesRegistryEntry(SliderParameter::class.java, SliderParameter::name, ::SliderProperty))
        properties.add(PropertiesRegistryEntry(RangeSliderParameter::class.java, RangeSliderParameter::name, ::RangeSliderProperty))
        properties.add(PropertiesRegistryEntry(NumberParameter::class.java, NumberParameter::name, ::NumberProperty))
        properties.add(PropertiesRegistryEntry(ProgressParameter::class.java, ProgressParameter::name, ::ProgressProperty))
        properties.add(PropertiesRegistryEntry(BooleanParameter::class.java, BooleanParameter::name, ::BooleanProperty))
        properties.add(PropertiesRegistryEntry(Float2Parameter::class.java, Float2Parameter::name, ::Float2Property))
        properties.add(PropertiesRegistryEntry(Float3Parameter::class.java, Float3Parameter::name, ::Float3Property))
        properties.add(PropertiesRegistryEntry(ActionParameter::class.java, ActionParameter::name, ::ActionProperty))
        properties.add(PropertiesRegistryEntry(EnumParameter::class.java, EnumParameter::name, ::EnumProperty))
        properties.add(PropertiesRegistryEntry(ColorParameter::class.java, ColorParameter::name, ::ColorProperty))
        properties.add(PropertiesRegistryEntry(ListParameter::class.java, ListParameter::name, ::ListProperty))
        properties.add(PropertiesRegistryEntry(PathParameter::class.java, PathParameter::name, ::PathProperty))
        properties.add(PropertiesRegistryEntry(SelectableListParameter::class.java, SelectableListParameter::name, ::SelectableListProperty))

        properties.add(PropertiesRegistryEntry(LabelParameter::class.java, LabelParameter::name, ::LabelProperty))
        properties.add(PropertiesRegistryEntry(GroupParameter::class.java, GroupParameter::name, ::GroupProperty))
    }
}