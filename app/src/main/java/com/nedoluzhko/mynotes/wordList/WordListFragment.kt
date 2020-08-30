package com.nedoluzhko.mynotes.wordList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nedoluzhko.mynotes.R
import com.nedoluzhko.mynotes.databinding.WordListFragmentBinding

class WordListFragment : Fragment(), WordListAdapter.WordListListener {

    private val classTag = "WordListFragment"

    private lateinit var viewModel: WordListViewModel
    private lateinit var binding: WordListFragmentBinding

    private var actionMode: ActionMode? = null
    lateinit var tracker: SelectionTracker<Long>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.word_list_fragment,
            container,
            false
        )

        // ViewModel
        val application = requireNotNull(this.activity).application
        val viewModelFactory = WordListViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WordListViewModel::class.java)
        binding.wordListViewModel = viewModel
        binding.lifecycleOwner = this

        // RecyclerView
        val wordListAdapter = WordListAdapter(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wordListAdapter
        }
        configurateSelectionTracker(binding.recyclerView, wordListAdapter)

        // Observe block
        viewModel.allWords.observe(viewLifecycleOwner, { words ->
            binding.emptyListIndicator.visibility = if (0 == words.size) View.VISIBLE else View.GONE
            words?.let { wordListAdapter.data = words }
        })

        viewModel.navigateToNewWordFragment.observe(viewLifecycleOwner, { newStatus ->
            if (newStatus == true) {
                findNavController().navigate(R.id.action_wordListFragment_to_newWordFragment)
                viewModel.navigateToNewWordFragment.value = false
            }
        })
        return binding.root
    }

    private fun configurateSelectionTracker(
        rv: RecyclerView,
        wordListAdapter: WordListAdapter
    ) {
        tracker = SelectionTracker.Builder(
            "mySelection",
            rv,
            WordKeyProvider(rv),
            WordListLookup(rv),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything())
            .build()

        wordListAdapter.tracker = tracker
        viewModel.tracker = tracker

        tracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                super.onSelectionChanged()

                if (tracker.hasSelection() && actionMode == null) {
                    val actionModeCallback = ActionModeCallback(viewModel)
                    actionMode =
                        (activity as AppCompatActivity).startSupportActionMode(actionModeCallback)
                    actionMode!!.title = tracker.selection.size().toString()
                    Log.i(classTag, "ActionMode created")
                } else if (!tracker.hasSelection()) {
                    actionMode?.finish()
                    actionMode = null
                    Log.i(classTag, "ActionMode finished")
                } else {
                    actionMode!!.title = tracker.selection.size().toString()
                    Log.i(
                        classTag, """ActionMode updated:
                        |Selection size: ${tracker.selection.size()}
                        |Selected ids: ${viewModel.getSelectedItemsIds()}
                    """.trimMargin()
                    )
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.word_list_title)
    }

    override fun onItemClick(pos: Int) {
        if (viewModel.allWords.value != null) {
            viewModel.delete(viewModel.allWords.value!![pos])
        } else {
            Toast.makeText(context, "Item index: $pos", Toast.LENGTH_LONG).show()
        }
    }
}