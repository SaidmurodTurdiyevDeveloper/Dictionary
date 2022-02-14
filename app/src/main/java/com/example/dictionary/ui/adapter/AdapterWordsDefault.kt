package com.example.dictionary.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.data.model.DataWord
import com.example.dictionary.databinding.ItemWordBinding
import com.example.dictionary.utils.other.sendOneParametreBlock
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AdapterWordsDefault @Inject constructor(@ApplicationContext private var context: Context) : RecyclerView.Adapter<AdapterWordsDefault.ViewHolder>() {
    var differ = AsyncListDiffer(this, ITEM_DIFF)
        private set
    private var listener: sendOneParametreBlock<DataWord>? = null
    private var longPressListener: ((View, DataWord, Int) -> Unit)? = null

    fun submitlistener(block: sendOneParametreBlock<DataWord>) {
        listener = block
    }

    fun submitLongPressListener(block: (View, DataWord, Int) -> Unit) {
        longPressListener = block
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<DataWord>() {
            override fun areItemsTheSame(oldItem: DataWord, newItem: DataWord): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DataWord, newItem: DataWord): Boolean =
                oldItem.id == newItem.id &&
                        oldItem.wordFirst == newItem.wordFirst &&
                        oldItem.wordSecond == newItem.wordSecond &&
                        oldItem.example == newItem.example &&
                        oldItem.dictionaryId == newItem.dictionaryId &&
                        oldItem.isActiveSecond == newItem.isActiveSecond &&
                        oldItem.isActiveFirst == newItem.isActiveFirst
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(ItemWordBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(differ.currentList[position])

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private var binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.lyWordFirst.setOnClickListener {
                listener?.invoke(differ.currentList[bindingAdapterPosition])
            }
            binding.lyWordSecond.setOnClickListener {
                listener?.invoke(differ.currentList[bindingAdapterPosition])
            }
            binding.lyWordFirst.setOnLongClickListener {
                longPressListener?.invoke(
                    itemView,
                    differ.currentList[bindingAdapterPosition],
                    bindingAdapterPosition
                )
                true
            }
            binding.lyWordSecond.setOnLongClickListener {
                longPressListener?.invoke(
                    itemView,
                    differ.currentList[bindingAdapterPosition],
                    bindingAdapterPosition
                )
                true
            }
        }

        fun bind(data: DataWord) {
            binding.tvWordOne.text = data.wordFirst
            binding.tvWordTwo.text = data.wordSecond
        }
    }
}
