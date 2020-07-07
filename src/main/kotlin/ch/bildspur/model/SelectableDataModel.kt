package ch.bildspur.model

import com.google.gson.annotations.Expose

class SelectableDataModel<T>(items: MutableList<T> = mutableListOf(), @Expose private var index: Int = -1) : ListDataModel<T>(items) {
    init {
        if (index == -1 && size > 0)
            index = 0
    }

    var selectedItem: T
        get() = this.value[index]
        set(value) {
            this.selectedIndex = this.value.indexOf(value)
        }

    var selectedIndex: Int
        get() = index
        set(value) {
            this.index = value
            this.fire()
        }
}