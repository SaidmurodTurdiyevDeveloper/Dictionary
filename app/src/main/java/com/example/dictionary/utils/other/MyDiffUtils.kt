package com.example.dictionary.utils.other

import androidx.recyclerview.widget.DiffUtil
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity

class MyDiffUtils constructor(private var oldlist: List<DictionaryEntity>, private var newList: List<DictionaryEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldlist.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldlist[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldlist[oldItemPosition].id == newList[newItemPosition].id
            && oldlist[oldItemPosition].name == newList[newItemPosition].name
            && oldlist[oldItemPosition].dataInfo == newList[newItemPosition].dataInfo
            && oldlist[oldItemPosition].learnPracent == newList[newItemPosition].learnPracent
            && oldlist[oldItemPosition].languageIdOne == newList[newItemPosition].languageIdOne
            && oldlist[oldItemPosition].languageIdTwo == newList[newItemPosition].languageIdTwo
            && oldlist[oldItemPosition].isDelete == newList[newItemPosition].isDelete
            && oldlist[oldItemPosition].isSelect == newList[newItemPosition].isSelect

}