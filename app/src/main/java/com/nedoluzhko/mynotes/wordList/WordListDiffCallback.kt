package com.nedoluzhko.mynotes.wordList

import androidx.recyclerview.widget.DiffUtil
import com.nedoluzhko.mynotes.database.WordEntity

class WordListDiffCallback(
    private val oldData: List<WordEntity>,
    private val newData: List<WordEntity>
) : DiffUtil.Callback() {

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