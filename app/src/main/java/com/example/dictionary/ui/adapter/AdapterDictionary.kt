package com.example.dictionary.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.databinding.ItemDictionaryBinding
import com.example.dictionary.utils.other.MyDiffUtils
import com.example.dictionary.utils.other.sendOneParametreBlock
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AdapterDictionary @Inject constructor(@ApplicationContext private var myContext: Context) :
    RecyclerView.Adapter<AdapterDictionary.ViewHolder>() {
    private var differ = emptyList<DictionaryEntity>()
    private var listenerItem: sendOneParametreBlock<DictionaryEntity>? = null
    private var longPressListener: ((View, DictionaryEntity, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDictionaryBinding.inflate(LayoutInflater.from(myContext), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ[position])
    }

    fun submitList(ls: List<DictionaryEntity>) {
        val diffUtil = MyDiffUtils(differ, ls)
        val difResult = DiffUtil.calculateDiff(diffUtil)
        differ = ls
        difResult.dispatchUpdatesTo(this)
    }

    fun submitListenerItemTouch(block: sendOneParametreBlock<DictionaryEntity>) {
        listenerItem = block
    }

    fun submitLongPressListener(block: (View, DictionaryEntity, Int) -> Unit) {
        longPressListener = block
    }

    inner class ViewHolder(private var binding: ItemDictionaryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                listenerItem?.invoke(differ[bindingAdapterPosition])
            }
            itemView.setOnLongClickListener {
                longPressListener?.invoke(
                    itemView,
                    differ[bindingAdapterPosition],
                    bindingAdapterPosition
                )
                true
            }
        }

        @SuppressLint("ResourceAsColor")
        fun bind(data: DictionaryEntity) {
            binding.apply {
                if (data.isSelect) {
                    item.setBackgroundResource(R.drawable.day_night_select_item)
                } else {
                    when {
                        data.learnPracent < 50 -> {
                            item.setBackgroundColor(Color.parseColor("#26FF0000"))
                        }
                        data.learnPracent > 50 && data.learnPracent < 100 -> {
                            item.setBackgroundColor(Color.parseColor("#26D9FF00"))
                        }
                        else -> item.setBackgroundColor(Color.parseColor("#2604B149"))
                    }
                }
                dictionaryName.text = data.name
            }
        }
    }
}