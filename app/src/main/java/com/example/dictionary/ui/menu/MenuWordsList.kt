package com.example.dictionary.ui.menu

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import com.example.dictionary.R
import com.example.dictionary.utils.other.emptyBlock

class MenuWordsList(context: Context, view: View) {
    private val popupMenu: PopupMenu = PopupMenu(context, view)
    private var firstToSecondListener: emptyBlock? = null
    private var secondToFirstListener: emptyBlock? = null
    private var openAllListener: emptyBlock? = null

    init {
        popupMenu.menuInflater.inflate(R.menu.menu_word_list, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.first_to_second -> firstToSecondListener?.invoke()
                R.id.second_to_first -> secondToFirstListener?.invoke()
                R.id.open_all -> openAllListener?.invoke()
            }
            true
        }
    }

    fun setFirstToSecondListener(block: emptyBlock): MenuWordsList {
        firstToSecondListener = block
        return this
    }

    fun setSecondToFirstListener(block: emptyBlock): MenuWordsList {
        secondToFirstListener = block
        return this
    }

    fun setOpenAllListener(block: emptyBlock): MenuWordsList {
        openAllListener = block
        return this
    }

    fun show() {
        popupMenu.show()
    }
}