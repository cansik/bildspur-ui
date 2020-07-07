package ch.bildspur.test

import ch.bildspur.color.RGB
import ch.bildspur.configuration.ConfigurationController
import ch.bildspur.math.Float2
import ch.bildspur.math.Float3
import ch.bildspur.model.DataModel
import ch.bildspur.model.ListDataModel
import ch.bildspur.model.NumberRange
import ch.bildspur.model.SelectableDataModel
import ch.bildspur.ui.AppConfiguration
import ch.bildspur.ui.ConfigurationWindow
import ch.bildspur.ui.properties.*
import com.google.gson.annotations.Expose
import javafx.application.Platform
import javafx.stage.Stage

fun main() {
    ConfigurationWindowTest().runConfigWindow()
}

data class MyColor(@Expose @SliderParameter("Red", 0.0, 1.0, 0.1) var red : DataModel<Float>,
                   @Expose @SliderParameter("Green", 0.0, 1.0, 0.1) var green : DataModel<Float>,
                   @Expose @SliderParameter("Blue", 0.0, 1.0, 0.1) var blue : DataModel<Float>)

class ConfigurationWindowTest {
    enum class Gender {
        Male, Female, Other
    }

    @AppConfiguration("Test")
    class AppConfig {

        @Expose
        var subConfig = SubConfig()

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
        @ProgressParameter("Progress")
        var progress = DataModel(0.647327)

        @Expose
        @BooleanParameter("Heating")
        var heating = DataModel(true)

        @Expose
        @ColorParameter("Sunset")
        var colorParameter = DataModel(RGB(0.2, 0.3, 0.1, 1.0))

        @Expose
        @GroupParameter("Color")
        val rgb = MyColor(DataModel(0.5f), DataModel(0.3f), DataModel(0.8f))

        @Expose
        @Float2Parameter("Position")
        var position = DataModel(Float2(20f, 50f))

        @Expose
        @Float3Parameter("Vertex")
        var vertex = DataModel(Float3(0.23f, 0.52f, 1.02f))

        @ActionParameter("Task", "Run", false)
        var task = {
            println("running...")
            Thread.sleep(500)
            println("Age: ${this.user.age}")
            Platform.runLater {
                userList.remove("Max")
            }
            println("done!")
        }

        @Expose
        @ListParameter("Users")
        var userList = ListDataModel(mutableListOf("Max", "Tim", "Florian"))

        @Expose
        @SelectableListParameter("City")
        var cities = SelectableDataModel(mutableListOf("Zurich", "Paris", "Berlin"))

        @Expose
        @GroupParameter("User")
        var user = User()
        class User {
            @Expose
            @StringParameter("Name")
            var name = DataModel("Max")

            @Expose
            @NumberParameter("Age")
            var age = DataModel(23)

            @Expose
            @SliderParameter("Happyness", 0.0, 100.0, 1.0, true, true)
            var happyness = DataModel(80)
        }

        @Expose
        @EnumParameter("Gender")
        var gender = DataModel(Gender.Female)

        @Expose
        @LabelParameter("Test")
        val label = Any()
    }

    @AppConfiguration("Light")
    class SubConfig {
        @Expose
        var dmx = DMXConfig()

        @Expose
        @LabelParameter("Sub Config Label")
        val label = Any()

        @Expose
        val list = ListDataModel(mutableListOf(2, 3, 5, 2, 3))
    }

    @AppConfiguration("DMX")
    class DMXConfig {
        @Expose
        @LabelParameter("DMX Test")
        val label = Any()
    }

    fun runConfigWindow() {
        Platform.startup {
            val controller = ConfigurationController("CWT", "bildspur", "test")
            val cfg = controller.loadAppConfig<AppConfig>()

            cfg.cities.onChanged += {
                println("Cities Changed: ${cfg.cities.selectedIndex}")
            }
            cfg.cities.fireLatest()

            val window = ConfigurationWindow(controller, "CWT", cfg)
            window.start(Stage())
        }
    }
}