package ch.bildspur.test

import ch.bildspur.configuration.ConfigurationController
import ch.bildspur.model.DataModel
import ch.bildspur.ui.ConfigurationWindow
import ch.bildspur.ui.properties.StringParameter
import com.google.gson.annotations.Expose
import javafx.application.Platform
import javafx.stage.Stage
import org.junit.Test

fun main() {
    ConfigurationWindowTest().runConfigWindow()
}

class ConfigurationWindowTest {
    class AppConfig {
        @Expose
        @StringParameter("Name")
        var name = DataModel("bildspur")
    }

    fun runConfigWindow() {
        Platform.startup {
            val controller = ConfigurationController("CWT", "bildspur", "test")
            val window = ConfigurationWindow(controller, AppConfig(), "CWT")
            window.start(Stage())
        }
    }
}