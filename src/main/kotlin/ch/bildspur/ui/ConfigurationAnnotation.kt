package ch.bildspur.ui

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class AppConfiguration(val name: String)