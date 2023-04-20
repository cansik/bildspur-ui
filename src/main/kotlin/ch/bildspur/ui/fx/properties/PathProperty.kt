package ch.bildspur.ui.fx.properties

import ch.bildspur.model.DataModel
import ch.bildspur.ui.fx.BaseFXFieldProperty
import ch.bildspur.ui.fx.controls.EditTextField
import ch.bildspur.ui.fx.utils.FileChooserDialogMode
import ch.bildspur.ui.properties.PathParameter
import javafx.application.Platform
import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.stage.DirectoryChooser
import java.lang.reflect.Field
import javafx.stage.FileChooser
import java.io.File
import java.lang.Exception
import java.nio.file.*


@Suppress("UNCHECKED_CAST")
class PathProperty(field: Field, obj: Any, val annotation: PathParameter) : BaseFXFieldProperty(field, obj) {

    val textField = EditTextField()
    val button = Button("...")
    val box = HBox(textField, button)

    init {
        // setup style
        setHgrow(textField, Priority.ALWAYS)
        textField.isEditable = annotation.isEditable
        applyStyle()
        setHgrow(box, Priority.ALWAYS)
        box.spacing = 5.0
        button.prefHeight = textField.prefHeight

        children.add(box)

        // setup binding
        val model = field.get(obj) as DataModel<Path>
        model.onChanged += {
            Platform.runLater {
                textField.text = model.value.toString()
            }
        }
        model.fireLatest()

        textField.setOnAction {
            try {
                model.value = Paths.get(textField.text)
            } catch (ex: InvalidPathException) {
                println("Not a valid path: ${textField.text}")
                return@setOnAction
            }
            propertyChanged(this)
            applyStyle()
        }

        button.setOnAction {
            val root = Paths.get("").toAbsolutePath()
            val current = model.value

            val path = when (annotation.mode) {
                FileChooserDialogMode.Open -> startFileDialog(root, current)
                FileChooserDialogMode.OpenDirectory -> startDirectoryDialog(root, current)
                FileChooserDialogMode.Save -> startFileDialog(root, current)
            }

            if (path != null) {
                var correctPath: Path = path
                if (annotation.relativizePath && path.startsWith(root)) {
                    correctPath = root.relativize(correctPath)
                }

                model.value = correctPath
                propertyChanged(this)
            }
        }
    }

    private fun startFileDialog(root: Path, current: Path): Path? {
        val fileChooser = FileChooser()

        // setup file chooser
        fileChooser.title = annotation.title

        fileChooser.initialDirectory = root.toFile()

        if (annotation.initialDirectory.isNotBlank()) {
            fileChooser.initialDirectory = File(annotation.initialDirectory)
        }

        if (annotation.initialFileName.isNotBlank()) {
            fileChooser.initialFileName = annotation.initialFileName
        }

        if (current.toString().isNotBlank() && Files.exists(current)) {
            if (Files.isDirectory(current)) {
                fileChooser.initialDirectory = current.toFile()
            }

            if (Files.isRegularFile(current)) {
                fileChooser.initialDirectory = current.toAbsolutePath().parent.toFile()
                fileChooser.initialFileName = current.fileName.toString()
            }
        }

        annotation.extensions.forEach {
            fileChooser.extensionFilters.add(FileChooser.ExtensionFilter(it, it))
        }

        // run file chooser
        val file = when (annotation.mode) {
            FileChooserDialogMode.Open -> fileChooser.showOpenDialog(this.scene.window)
            FileChooserDialogMode.Save -> fileChooser.showSaveDialog(this.scene.window)
            else -> throw Exception("There should be no directory chooser.")
        }

        if (file != null) {
            return file.toPath()
        }

        return null
    }

    private fun startDirectoryDialog(root: Path, current: Path): Path? {
        val dirChooser = DirectoryChooser()

        // setup file chooser
        dirChooser.title = annotation.title

        dirChooser.initialDirectory = root.toFile()

        if (annotation.initialDirectory.isNotBlank()) {
            dirChooser.initialDirectory = File(annotation.initialDirectory)
        }

        if (current.toString().isNotBlank() && Files.exists(current)) {
            if (Files.isDirectory(current)) {
                dirChooser.initialDirectory = current.toFile()
            }

            if (Files.isRegularFile(current)) {
                dirChooser.initialDirectory = current.toAbsolutePath().parent.toFile()
            }
        }

        // run file chooser
        val dir = dirChooser.showDialog(this.scene.window)

        if (dir != null) {
            return dir.toPath()
        }

        return null
    }


    private fun applyStyle() {
        if (annotation.isEditable) {
            textField.style = ""
        } else {
            // set to read only
            textField.style = "-fx-background-color: rgba(200, 200, 200, 0.3);\n" +
                    "-fx-border-color: rgba(200, 200, 200, 1.0);\n" +
                    "-fx-border-width: 1px;"
        }
    }
}