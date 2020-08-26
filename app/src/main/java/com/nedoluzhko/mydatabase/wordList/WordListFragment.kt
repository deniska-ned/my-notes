package com.nedoluzhko.mydatabase.wordList

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nedoluzhko.mydatabase.R
import com.nedoluzhko.mydatabase.databinding.WordListFragmentBinding

class WordListFragment : Fragment(), WordListAdapter.WordListListener {

    private lateinit var viewModel: WordListViewModel
    private lateinit var binding: WordListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.word_list_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = WordListViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WordListViewModel::class.java)
        binding.wordListViewModel = viewModel
        binding.lifecycleOwner = this

        val wordListAdapter = WordListAdapter(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = wordListAdapter
        }

        viewModel.allWords.observe(viewLifecycleOwner, { words ->
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.word_list_title)
    }

    override fun onItemClick(pos: Int) {
        viewModel.allWords.value?.get(pos)?.let { viewModel.delete(it) } ?: run {
            Toast.makeText(context, "Item is not deleted", Toast.LENGTH_LONG).show()
        }
        Toast.makeText(context, "Message $pos", Toast.LENGTH_LONG).show()
    }
}