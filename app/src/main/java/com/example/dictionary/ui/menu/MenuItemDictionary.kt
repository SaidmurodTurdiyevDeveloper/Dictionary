package com.example.dictionary.ui.menu

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import com.example.dictionary.R
import com.example.dictionary.utils.other.emptyBlock

class MenuItemDictionary(var context: Context, view: View) {

    private val popupMenu: PopupMenu = PopupMenu(context, view)

    private var listenerOpenDictionary: emptyBlock? = null
    private var listenerSelect: emptyBlock? = null
    private var listenerUpdate: emptyBlock? = null
    private var listenerDelete: emptyBlock? = null

    fun setListnerOpenThisDictionary(block: emptyBlock): MenuItemDictionary {
        listenerOpenDictionary = block
        return this
    }

    fun setlistenerSelect(block: emptyBlock): MenuItemDictionary {
        listenerSelect = block
        return this
    }

    fun setlistenerUpdate(block: emptyBlock): MenuItemDictionary {
        listenerUpdate = block
        return this
    }

    fun setlistenerDelete(block: emptyBlock): MenuItemDictionary {
        listenerDelete = block
        return this
    }

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