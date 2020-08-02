package ch.bildspur.ui.fx.controls

import javafx.event.ActionEvent
import javafx.scene.control.TextField

open class EditTextField : TextField() {
    private var textHasBeenEdited = false

    init {
        // set height
        prefHeight = 32.0

        // detect change
        this.setOnKeyTyped {
            textHasBeenEdited = true
        }

        this.focusedProperty().addListener { _, _, _ ->
            if(textHasBeenEdited && !isFocused) {
                // user left after changes -> fire action
                textHasBeenEdited = false
                fireEvent( ActionEvent())
            }
        }
    }
}