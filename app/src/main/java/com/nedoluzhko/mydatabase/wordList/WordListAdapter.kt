package com.nedoluzhko.mydatabase.wordList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nedoluzhko.mydatabase.R
import com.nedoluzhko.mydatabase.database.WordEntity
import kotlinx.coroutines.withContext

class WordListAdapter(private val listener: WordListListener) : RecyclerView.Adapter<WordListAdapter
.MyViewHolder>() {

    private var myData: List<WordEntity> = emptyList()

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(view: View, private val listener: WordListListener) :
        RecyclerView.ViewHolder(view) {

        val resources = view.resources
        val textView: TextView = view.findViewById(R.id.itemTextView)

        init {
            textView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_list_recycler_item, parent, false)

        return MyViewHolder(view, listener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.text = holder.resources.getString(R.string.list_item_format,
            myData[position].id, myData[position].word)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myData.size

    fun setWords(words: List<WordEntity>) {
        this.myData = words
        notifyDataSetChanged()
    }

    interface WordListListener {
        fun onItemClick(pos: Int)
    }
}
