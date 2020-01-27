package ch.bildspur.ui

import ch.bildspur.configuration.ConfigurationController
import ch.bildspur.ui.properties.PropertiesControl
import javafx.application.Application
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.stage.Screen
import javafx.stage.Stage

class ConfigurationWindow(val configController : ConfigurationController, val config : Any, val title : String) : Application() {
    private val propertiesControl = PropertiesControl()

    override fun start(primaryStage: Stage) {
        primaryStage.title = title

        val root = createUI(primaryStage)
        primaryStage.scene = Scene(root, 420.0, 700.0)
        primaryStage.setOnShown {
            propertiesControl.resize(primaryStage.scene.width, primaryStage.scene.height)
        }

        primaryStage.setOnCloseRequest {
        }

        val primScreenBounds = Screen.getPrimary().visualBounds
        primaryStage.x = primScreenBounds.width / 8.0 * 7.0

        primaryStage.show()
    }

    private fun createUI(primaryStage: Stage) : Pane {
        // components
        val saveButton = Button("Save")
        saveButton.setOnAction {
            configController.saveAppConfig(config)
            println("config saved!")
            saveButton.style = "-fx-text-fill: #000000"
            primaryStage.title = title
        }
        saveButton.style = "-fx-text-fill: #000000"

        propertiesControl.initView(config)
        propertiesControl.propertyChanged += {
            primaryStage.title = "$title*"
            saveButton.style = "-fx-text-fill: #ff7675"
        }

        val spacerButton = Button("")
        spacerButton.isDisable = true

        val top = HBox(saveButton, spacerButton)

        val settings = mapOf(
            "General" to config
            )

        settings.forEach { (name, cfg) ->
            val button = Button(name)
            button.setOnAction { propertiesControl.initView(cfg) }
            button.style = "-fx-font-size: 1em;"
            top.children.add(button)
        }

        // layout
        top.children.filterIsInstance<Button>().forEach {
            it.padding = Insets(5.0)
        }
        top.padding = Insets(10.0)
        top.spacing = 5.0
        return BorderPane(ScrollPane(propertiesControl), top, null, null, null)
    }
}