package com.example.dictionary.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.data.model.DataWord
import com.example.dictionary.databinding.ItemWordBinding
import com.example.dictionary.utils.other.sendOneParametreBlock
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AdapterWord @Inject constructor(@ApplicationContext private var context: Context) : RecyclerView.Adapter<AdapterWord.ViewHolder>() {
    var differ = AsyncListDiffer(this, ITEM_DIFF)
        private set
    private var listenerFirst: sendOneParametreBlock<DataWord>? = null
    private var listenerSecond: sendOneParametreBlock<DataWord>? = null

    fun submitlistenerFirst(block: sendOneParametreBlock<DataWord>) {
        listenerFirst = block
    }

    fun submitlistenerSecond(block: sendOneParametreBlock<DataWord>) {
        listenerSecond = block
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
                listenerFirst?.invoke(differ.currentList[bindingAdapterPosition])
            }
            binding.lyWordSecond.setOnClickListener {
                listenerSecond?.invoke(differ.currentList[bindingAdapterPosition])
            }
        }

        fun bind(data: DataWord) {
            if (data.isActiveFirst) {
                binding.lyWordFirst.setBackgroundColor(Color.parseColor("#D1C4E9"))
                binding.tvWordOne.text = data.wordFirst
            } else {
                binding.tvWordOne.text = ""
                binding.lyWordFirst.setBackgroundColor(Color.parseColor("#F8BBD0"))
            }
            if (data.isActiveSecond) {
                binding.lyWordSecond.setBackgroundColor(Color.parseColor("#D1C4E9"))
                binding.tvWordTwo.text = data.wordFirst
            } else {
                binding.tvWordTwo.text = ""
                binding.lyWordSecond.setBackgroundColor(Color.parseColor("#F8BBD0"))
            }
        }
    }
}