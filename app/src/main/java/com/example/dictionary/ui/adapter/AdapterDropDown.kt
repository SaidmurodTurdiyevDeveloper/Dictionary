package com.example.dictionary.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.dictionary.R
import com.example.dictionary.data.model.DataCountry

class AdapterDropDown(myContext: Context, var list: List<DataCountry>) : ArrayAdapter<DataCountry>(myContext, 0, list) {

    private var customLayoutinflater = LayoutInflater.from(myContext)

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View = view(customLayoutinflater.inflate(R.layout.screen_spinner_country, null, true), position)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cv = convertView
        if (cv == null) cv = customLayoutinflater.inflate(R.layout.item_drop_down_country, parent, false)
        return view(cv!!, position)
    }

    @SuppressLint("SetTextI18n")
    private fun view(view: View, position: Int): View {
        val flag = view.findViewById<ImageView>(R.id.country_item_flag)
        val text = view.findViewById<TextView>(R.id.country_item_text)
        try {
            val d = list[position]
            flag.setImageResource(d.flag)
            text.text = d.country
        } catch (e: Exception) {
            flag.setImageResource(R.drawable.country_uzb)
            text.text="Error"
        }
        return view
    }

}