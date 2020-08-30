package com.nedoluzhko.mynotes.wordList

import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.widget.RecyclerView

class WordKeyProvider(private val recyclerView: RecyclerView) :
    ItemKeyProvider<Long>(SCOPE_CACHED) {
    override fun getKey(position: Int): Long? {
        val adapter = recyclerView.adapter as WordListAdapter
        return adapter.data.getOrNull(position)?.id
    }

    override fun getPosition(key: Long): Int {
        val adapter = recyclerView.adapter as WordListAdapter
        val items = adapter.data

        for (i in items.indices) {
            if (key == items[i].id) return i
        }

        return -1
//        throw Exception("WordKeyProvider.getPosition failed")
    }
}
