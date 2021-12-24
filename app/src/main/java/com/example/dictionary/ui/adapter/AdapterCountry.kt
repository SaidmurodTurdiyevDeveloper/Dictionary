package com.example.dictionary.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.data.model.DataCountry
import com.example.dictionary.databinding.ItemCountryBinding


class AdapterCountry(
    var myContext: Context,
    var list: List<DataCountry>,
    var countryOneData: Int? = null
) :
    RecyclerView.Adapter<AdapterCountry.ViewHolder>() {
    private var listener: ((Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(myContext).inflate(R.layout.item_country, parent, false)
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    override fun getItemCount(): Int = list.size


    fun submitlistener(block: ((Int) -> Unit)) {
        listener = block
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                listener?.invoke(bindingAdapterPosition)
            }
        }

        fun bind() {
            ItemCountryBinding.bind(itemView).apply {
                val data = list[bindingAdapterPosition]
                if (bindingAdapterPosition == countryOneData) {
                    item.setBackgroundColor(Color.parseColor("#1AFF0000"))
                    itemView.setOnClickListener { }
                    try {
                        itemView.tag = "Ishlagan"
                    } catch (e: Exception) {
                        Toast.makeText(myContext, "${e.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    item.setBackgroundColor(Color.parseColor("#00FF0000"))
                    var t: String
                    try {
                        t = (itemView.tag as String)
                    } catch (e: Exception) {
                        t = "Ishlamagan"
                    }
                    if (t == "Ishlagan")
                        itemView.setOnClickListener {
                            listener?.invoke(bindingAdapterPosition)
                        }
                }
                countryItemFlag.setImageResource(data.flag)
                countryItemText.text = data.country
            }
        }
    }
}
