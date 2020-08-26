package com.nedoluzhko.mydatabase.wordList

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nedoluzhko.mydatabase.R
import com.nedoluzhko.mydatabase.database.WordEntity
import com.nedoluzhko.mydatabase.databinding.WordListRecyclerItemBinding

class WordListAdapter(private val listener: WordListListener) :
    RecyclerView.Adapter<WordListAdapter.MyViewHolder>() {

    var data: List<WordEntity> = emptyList()
        set(newData) {
            val diffCallback = WordListDiffCallback(data, newData)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            field = newData
            diffResult.dispatchUpdatesTo(this)
        }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(
        binding: WordListRecyclerItemBinding,
        private val listener: WordListListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        val resources = binding.root.context.resources

        val textView = binding.itemTextView

        init {
            textView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

        fun bind(item: WordEntity) {
            textView.text = resources.getString(R.string.list_item_format, item.id, item.word)
        }

        companion object {
            fun from(parent: ViewGroup, listener: WordListListener): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WordListRecyclerItemBinding.inflate(
                    layoutInflater, parent, false
                )

                return MyViewHolder(binding, listener)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent, listener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size

    interface WordListListener {
        fun onItemClick(pos: Int)
    }
}
