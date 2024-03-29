package ch.bildspur.ui.properties

import ch.bildspur.ui.fx.utils.FileChooserDialogMode
import ch.bildspur.util.Mapping

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
                                 val roundInt: Boolean = false,
                                 val mapping : Mapping = Mapping.Linear,
                                 val labelDigits : Int = 2)

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
annotation class ProgressParameter(val name: String, val displayValue : Boolean = true)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class BooleanParameter(val name: String, val useToggleSwitch : Boolean = false)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Float2Parameter(val name: String)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Float3Parameter(val name: String)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ActionParameter(val name: String, val caption: String, val invokesChange : Boolean = true, val uiThread: Boolean = false)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnumParameter(val name: String)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ColorParameter(val name: String)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class LabelParameter(val name: String, val fontSize : Double = 14.0)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class GroupParameter(val name: String, val expanded : Boolean = true, val collapsible : Boolean = true)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ListParameter(val name: String, val height : Double = 100.0)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class SelectableListParameter(val name: String)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class PathParameter(val name: String,
                               val title: String = "Open",
                               val mode : FileChooserDialogMode = FileChooserDialogMode.Open,
                               val initialDirectory : String = "",
                               val initialFileName: String = "",
                               val extensions : Array<String> = [],
                               val relativizePath : Boolean = true,
                               val isEditable: Boolean = true)