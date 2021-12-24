package com.example.dictionary.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.databinding.ItemDictionaryBinding

class AdapterDictionaryWithSelect(private var myContext: Context) :
    RecyclerView.Adapter<AdapterDictionaryWithSelect.ViewHolder>() {
    private val differ = AsyncListDiffer(this, ITEM_DIFF)
    private var listenerSelect: ((Int) -> Unit)? = null
    private var listenerItem: ((Long) -> Unit)? = null
    private var isSelected = true
    private var longPressListener: ((View, DictionaryEntity, Int) -> Unit)? = null

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<DictionaryEntity>() {
            override fun areItemsTheSame(oldItem: DictionaryEntity, newItem: DictionaryEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: DictionaryEntity,
                newItem: DictionaryEntity
            ) = oldItem.name == newItem.name
                    && oldItem.learnPracent == newItem.learnPracent
                    && oldItem.languageIdOne == newItem.languageIdOne
                    && oldItem.languageIdTwo == newItem.languageIdTwo
                    && oldItem.isSelect == newItem.isSelect
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(myContext).inflate(R.layout.item_dictionary, parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    fun submitList(ls: List<DictionaryEntity>) {
        differ.submitList(ls)
    }

    fun submitListenerItemOpen(block: (Long) -> Unit) {
        listenerItem = block
    }

    fun selectedDismiss() {
        isSelected = true
    }

    fun submitListenerItemSelect(block: (Int) -> Unit) {
        listenerSelect = block
    }

    fun submitLongPressListener(block: (View, DictionaryEntity, Int) -> Unit) {
        longPressListener = block
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                if (isSelected)
                    listenerItem?.invoke(differ.currentList[absoluteAdapterPosition].id)
                else {
                    val selectPosition = absoluteAdapterPosition
                    if (differ.currentList.size > selectPosition)
                        listenerSelect?.invoke(selectPosition)
                }
            }
            itemView.setOnLongClickListener {
                if (isSelected) {
                    longPressListener?.invoke(
                        itemView,
                        differ.currentList[absoluteAdapterPosition],
                        absoluteAdapterPosition
                    )
                    isSelected = false
                }
                true
            }
        }

        @SuppressLint("ResourceAsColor")
        fun bind() {
            ItemDictionaryBinding.bind(itemView).apply {
                val d = differ.currentList[bindingAdapterPosition]
                if (d.isSelect) {
                    item.setBackgroundResource(R.drawable.day_night_select_item)
                } else {
                    when {
                        d.learnPracent < 50 -> {
                            item.setBackgroundColor(R.color.degreeOne)
                        }
                        d.learnPracent != 100 -> {
                            item.setBackgroundColor(R.color.degreeTwo)
                        }
                        else -> item.setBackgroundColor(R.color.degreeThree)
                    }
                }
                dictionaryName.text = d.name
            }

        }
    }
}