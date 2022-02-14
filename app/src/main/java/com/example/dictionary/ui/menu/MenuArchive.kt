package com.example.dictionary.ui.menu

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import com.example.dictionary.R
import com.example.dictionary.utils.other.emptyBlock

class MenuArchive(context: Context, view: View) {
    private val popupMenu: PopupMenu = PopupMenu(context, view)
    private var clearListener: emptyBlock? = null

    init {
        popupMenu.menuInflater.inflate(R.menu.menu_archive, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_item_clear_all -> clearListener?.invoke()
            }
            true
        }
    }

    fun setClearListener(block: emptyBlock): MenuArchive {
        clearListener = block
        return this
    }

    fun show() {
        popupMenu.show()
    }
}