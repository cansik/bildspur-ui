package ch.bildspur.ui.html

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class HTMLElement(val id: String)