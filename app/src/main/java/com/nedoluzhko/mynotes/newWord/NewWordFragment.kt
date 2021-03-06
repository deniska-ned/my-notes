package com.nedoluzhko.mynotes.newWord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nedoluzhko.mynotes.R
import com.nedoluzhko.mynotes.databinding.NewWordFragmentBinding

class NewWordFragment : Fragment() {

    private lateinit var viewModel: NewWordViewModel
    private lateinit var binding: NewWordFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.new_word_fragment,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = NewWordViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewWordViewModel::class.java)

        binding.newWordViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToWordListFragment.observe(viewLifecycleOwner, { newStatus ->
            if (newStatus == true) {
                findNavController().popBackStack()
//                viewModel.navigateToWordListFragment.value = false
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.new_word_title)
    }
}