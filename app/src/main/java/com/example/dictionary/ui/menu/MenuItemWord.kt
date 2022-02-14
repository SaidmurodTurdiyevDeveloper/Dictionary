package com.example.dictionary.ui.menu

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import com.example.dictionary.R
import com.example.dictionary.utils.other.emptyBlock

class MenuItemWord(var context: Context, view: View) {
    private val popupMenu: PopupMenu = PopupMenu(context, view)

    private var listenerUpdate: emptyBlock? = null
    private var listenerDelete: emptyBlock? = null


    fun setlistenerUpdate(block: emptyBlock): MenuItemWord {
        listenerUpdate = block
        return this
    }

    fun setlistenerDelete(block: emptyBlock): MenuItemWord {
        listenerDelete = block
        return this
    }

    init {
        popupMenu.menuInflater.inflate(R.menu.menu_item_word, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.update_word_menu -> {
                    listenerUpdate?.invoke()
                }
                R.id.delete_word_menu -> {
                    listenerDelete?.invoke()
                }
            }
            true
        }
    }

    fun show() {
        if (Build.VERSION.SDK_INT >= 23)
            popupMenu.gravity = Gravity.END
        popupMenu.show()
    }
}