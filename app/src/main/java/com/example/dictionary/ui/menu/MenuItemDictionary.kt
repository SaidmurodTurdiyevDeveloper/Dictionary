package com.example.dictionary.ui.menu

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import com.example.dictionary.R

class MenuItemDictionary(
    var context: Context,
    view: View,
    listenerOpenDictionary: (() -> Unit)?,
    listenerSelect: (() -> Unit)?,
    listenerUpdate: (() -> Unit)?,
    listenerDelete: (() -> Unit)?
) {
    private val popupMenu: PopupMenu = PopupMenu(context, view)

    init {
        popupMenu.menuInflater.inflate(R.menu.menu_item_dictionary, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.open_item_dictionary_menu -> {
                    listenerOpenDictionary?.invoke()
                }
                R.id.check_dictionary_menu -> {
                    listenerSelect?.invoke()
                }
                R.id.update_dictionary_menu -> {
                    listenerUpdate?.invoke()
                }
                R.id.delete_dictionary_menu -> {
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