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

class AdapterDictionary(private var myContext: Context) :
    RecyclerView.Adapter<AdapterDictionary.ViewHolder>() {
    protected val differ = AsyncListDiffer(this, ITEM_DIFF)
    protected var listenerItem: ((DictionaryEntity) -> Unit)? = null

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

    fun submitListenerItemTouch(block: (DictionaryEntity) -> Unit) {
        listenerItem = block
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                listenerItem?.invoke(differ.currentList[absoluteAdapterPosition])
            }
        }

        @SuppressLint("ResourceAsColor")
        fun bind() {
            ItemDictionaryBinding.bind(itemView).apply {
                val d = differ.currentList[bindingAdapterPosition]
                if (d.learnPracent < 50) {
                    item.setBackgroundColor(R.color.degreeOne)
                } else if (d.learnPracent != 100) {
                    item.setBackgroundColor(R.color.degreeTwo)
                } else
                    item.setBackgroundColor(R.color.degreeThree)
                dictionaryName.text = d.name
            }
        }
    }
}