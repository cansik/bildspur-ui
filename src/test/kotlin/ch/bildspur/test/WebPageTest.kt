package ch.bildspur.test

import ch.bildspur.model.DataModel
import ch.bildspur.ui.AppConfiguration
import ch.bildspur.ui.html.HTMLElement
import ch.bildspur.ui.html.WebWindow
import ch.bildspur.ui.properties.ActionParameter
import ch.bildspur.ui.properties.StringParameter
import com.google.gson.annotations.Expose
import javafx.application.Platform
import javafx.stage.Stage

fun main() {
    WebPageTest().run()
}

class WebPageTest {
    @AppConfiguration("Test")
    class Model {

        @Expose
        @HTMLElement("name")
        @StringParameter("Name")
        var name = DataModel("bildspur")

        @HTMLElement("rename")
        @ActionParameter("Task", "Rename", false)
        var task = {
            Platform.runLater {
                println("Name: ${name.value}")
                name.value = "bsp studio"
            }
        }
    }

    fun run() {
        val model = Model()
        Platform.startup {
            val window = WebWindow("Web Window")
            window.webView.load("https://bildspur.ch")
            window.start(Stage())
        }
    }
}