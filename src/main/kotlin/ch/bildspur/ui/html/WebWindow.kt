package ch.bildspur.ui.html

import javafx.application.Application
import javafx.concurrent.WorkerStateEvent
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage

class WebWindow(val title : String, val htmlUI : String, val model : Any) : Application() {
    val propertyWebView = PropertyWebView(htmlUI)

    override fun start(primaryStage: Stage) {
        primaryStage.title = title

        val root = propertyWebView
        primaryStage.scene = Scene(root, 720.0, 640.0)

        primaryStage.setOnShown {
            propertyWebView.engine.loadWorker.stateProperty().addListener { ov, oldState, newState ->
                propertyWebView.bind(model)
            }
        }

        primaryStage.setOnCloseRequest {
        }

        // move to the right side of the screen
        val primScreenBounds = Screen.getPrimary().visualBounds
        primaryStage.x = primScreenBounds.width / 8.0 * 7.0 - primaryStage.scene.width

        primaryStage.show()
    }
}