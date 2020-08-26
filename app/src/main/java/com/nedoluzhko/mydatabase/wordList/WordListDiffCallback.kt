package com.nedoluzhko.mydatabase.wordList

import androidx.recyclerview.widget.DiffUtil
import com.nedoluzhko.mydatabase.database.WordEntity

class WordListDiffCallback(val oldData: List<WordEntity>, val newData: List<WordEntity>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition]
        val newItem = newData[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition]
        val newItem = newData[newItemPosition]
        return oldItem.word == newItem.word
    }
}