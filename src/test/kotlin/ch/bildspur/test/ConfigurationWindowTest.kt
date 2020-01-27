package ch.bildspur.test

import ch.bildspur.configuration.ConfigurationController
import ch.bildspur.math.Float2
import ch.bildspur.math.Float3
import ch.bildspur.model.DataModel
import ch.bildspur.model.NumberRange
import ch.bildspur.ui.ConfigurationWindow
import ch.bildspur.ui.properties.*
import com.google.gson.annotations.Expose
import javafx.application.Platform
import javafx.stage.Stage

fun main() {
    ConfigurationWindowTest().runConfigWindow()
}

class ConfigurationWindowTest {
    enum class Gender {
        Male, Female, Other
    }

    class AppConfig {
        @Expose
        @StringParameter("Name")
        var name = DataModel("bildspur")

        @Expose
        @SliderParameter("Temperature", 0.0, 100.0, 1.0, true, true)
        var temperature = DataModel(60)

        @Expose
        @RangeSliderParameter("Temperature Range", 0.0, 100.0, 1.0, true, true)
        var temperatureRange = DataModel(NumberRange(20.0, 80.0))

        @Expose
        @NumberParameter("Humidity", "%")
        var humidty = DataModel(80.0)

        @Expose
        @BooleanParameter("Heating")
        var heating = DataModel(true)

        @Expose
        @Float2Parameter("Position")
        var position = DataModel(Float2(20f, 50f))

        @Expose
        @Float3Parameter("Vertex")
        var vertex = DataModel(Float3(0.23f, 0.52f, 1.02f))

        @Expose
        @ActionParameter("Task", "Run")
        var task = {println("running...")}

        @Expose
        @EnumParameter("Gender")
        var gender = DataModel(Gender.Female)

        @Expose
        @LabelParameter("Test")
        val label = Any()
    }

    fun runConfigWindow() {
        Platform.startup {
            val controller = ConfigurationController("CWT", "bildspur", "test")
            val window = ConfigurationWindow(controller, AppConfig(), "CWT")
            window.start(Stage())
        }
    }
}