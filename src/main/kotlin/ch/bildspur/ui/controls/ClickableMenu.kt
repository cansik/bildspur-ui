package ch.bildspur.ui.controls

import javafx.beans.property.StringProperty
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.input.MouseEvent


class ClickableMenu @JvmOverloads constructor(title: String? = "") : Menu() {
    private val label: Label

    /**
     * This method should be used instead of [getText()][.getText] method.
     * @return title of this Menu
     */
    /**
     * This method should be used instead of [setText()][.setText] method.
     * @param text new title of this menu
     */
    var title: String?
        get() = label.text
        set(text) {
            label.text = text
        }

    /**
     * This method should be used instead of [textProperty()][.textProperty] method.
     * @return title property
     */
    fun titleProperty(): StringProperty {
        return label.textProperty()
    }
    /**
     * Creates new ClickableMenu with given title.
     * @param title initial title
     */
    /**
     * Creates new ClickableMenu without title.
     */
    init {
        // dummy item is needed to make JavaFX "believe", that menu item was pressed
        val dummyItem = MenuItem()
        dummyItem.isVisible = false
        items.add(dummyItem)
        label = Label()
        label.text = title
        label.padding = Insets(5.0)
        label.onMouseClicked = EventHandler { evt: MouseEvent? ->
            // forced child MenuItem click (this item is hidden, so this action is not visible but triggers parent "onAction" event handler anyway)
            dummyItem.fire()
        }
        graphic = label
    }
}