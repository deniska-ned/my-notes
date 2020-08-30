package com.nedoluzhko.mynotes.wordList

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.view.ActionMode
import com.nedoluzhko.mynotes.R

class ActionModeCallback(private val viewModel: WordListViewModel) : ActionMode.Callback {

    private val classTag = "ActionModeCallback"

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.contextual_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete -> {
                viewModel.deleteSelectedItems()
                mode?.finish()
                true
            }
            else -> false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        viewModel.tracker.clearSelection()
        Log.i(classTag, "Action bar mode closed")
    }
}