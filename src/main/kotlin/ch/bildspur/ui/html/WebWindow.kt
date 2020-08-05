package ch.bildspur.ui.html

import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage

class WebWindow(val title : String) : Application() {
    val webView = PropertyWebView()

    override fun start(primaryStage: Stage) {
        primaryStage.title = title

        val root = webView
        primaryStage.scene = Scene(root, 720.0, 640.0)

        primaryStage.setOnShown {
        }

        primaryStage.setOnCloseRequest {
        }

        // move to the right side of the screen
        val primScreenBounds = Screen.getPrimary().visualBounds
        primaryStage.x = primScreenBounds.width / 8.0 * 7.0 - primaryStage.scene.width

        primaryStage.show()
    }
}