package ch.bildspur.ui

import ch.bildspur.configuration.ConfigurationController
import ch.bildspur.ui.controls.ClickableMenu
import ch.bildspur.ui.properties.PropertiesControl
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.stage.Screen
import javafx.stage.Stage

class ConfigurationWindow(val configController : ConfigurationController, val title : String, val rootConfiguration: Any) : Application() {
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

        // move to the right side of the screen
        val primScreenBounds = Screen.getPrimary().visualBounds
        primaryStage.x = primScreenBounds.width / 8.0 * 7.0

        primaryStage.show()
    }

    private fun createUI(primaryStage: Stage) : Pane {
        // general components
        val saveItem = ClickableMenu("Save")
        saveItem.setOnAction {
            configController.saveAppConfig(rootConfiguration)
            println("config saved!")
            primaryStage.title = title
        }

        propertiesControl.initView(rootConfiguration)
        propertiesControl.propertyChanged += {
            primaryStage.title = "$title*"
        }

        val menuBar = MenuBar(saveItem)

        // load dynamic configurations
        val configurations = getAllAppConfigurations(rootConfiguration)
        configurations.add(0, rootConfiguration)

        // create configuration buttons
        val settings = configurations.map {
            val annotation = it.javaClass.getAnnotation(AppConfiguration::class.java)
            annotation to it
        }

        settings.forEach { (annotation, cfg) ->
            val menuButton = ClickableMenu(annotation?.name
                    ?: cfg.javaClass.name)
            menuButton.setOnAction { propertiesControl.initView(cfg) }
            menuBar.menus.add(menuButton)
        }

        return BorderPane(ScrollPane(propertiesControl), menuBar, null, null, null)
    }

    private fun getAllAppConfigurations(root : Any) : MutableList<Any> {
        val configurations = root.javaClass.declaredFields.filter {
            it.type.isAnnotationPresent(AppConfiguration::class.java)
        }.map {
            it.trySetAccessible()
            it.get(root)
        }.toMutableList()

        val subConfigurations = configurations.flatMap {
            getAllAppConfigurations(it)
        }

        configurations.addAll(subConfigurations)

        return configurations
    }
}