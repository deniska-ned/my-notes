package com.nedoluzhko.mydatabase.wordList

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
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

    lateinit var tracker: SelectionTracker<Long>

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(
        binding: WordListRecyclerItemBinding,
        private val listener: WordListListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private val resources: Resources = binding.root.context.resources
        private val textView = binding.itemTextView
        private lateinit var itemData: WordEntity

//        init {
//            textView.setOnClickListener {
//                listener.onItemClick(adapterPosition)
//            }
//        }

        fun bind(item: WordEntity, isActivated: Boolean = false) {
            itemData = item
            textView.text = resources.getString(
                R.string.test_list_item_format, itemId, item.id, item.word
            )
            textView.isActivated = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
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

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent, listener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, tracker.isSelected(data[position].id))
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = data.size


    interface WordListListener {
        fun onItemClick(pos: Int)
    }
}
