package com.vivian.mynotes.ui

import android.os.Bundle
import android.view.View
import android.view.View.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vivian.mynotes.R
import com.vivian.mynotes.adapter.NotesAdapter
import com.vivian.mynotes.databinding.FragmentAllNotesBinding
import com.vivian.mynotes.models.NoteEntity
import com.vivian.mynotes.ui.base.BaseFragment
import com.vivian.mynotes.utils.CoroutineUtils.executeInCoroutine
import com.vivian.mynotes.utils.Resource
import com.vivian.mynotes.utils.SnackBarUtils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllNotesFragment : BaseFragment<FragmentAllNotesBinding>() {

    private val viewModel by viewModels<AllNotesVM>()
    private val addNotesVM by viewModels<AddNotesVM>()

    override fun getViewBinding() = FragmentAllNotesBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        executeInCoroutine {
            viewModel.getSortCheckBoxState()

            viewModel.checkBoxState.observe(viewLifecycleOwner) { isChecked ->
                binding.checkboxSort.isChecked = isChecked
                viewModel.getNotes(isChecked)
            }

            viewModel.notes.observe(viewLifecycleOwner) { data ->
                when (data) {
                    is Resource.Loading -> showProgressBar()
                    is Resource.Success -> {
                        hideProgressBar()
                        if (data.data?.isEmpty() == true) {
                            binding.animNoTask.visibility = VISIBLE
                            binding.tvNoTasks.visibility = VISIBLE
                            binding.rvTasks.adapter = NotesAdapter(emptyList())
                            binding.checkboxSort.isChecked = false
                            enableSortCheckBox(false)
                            saveSortCheckBoxState(false)
                        } else {
                            binding.animNoTask.visibility = INVISIBLE
                            binding.tvNoTasks.visibility = INVISIBLE
                            binding.rvTasks.layoutManager = LinearLayoutManager(context)
                            binding.rvTasks.adapter = data.data?.let {
                                NotesAdapter(it) { taskItemBinding, item ->
                                    taskItemBinding.ivDelete.setOnClickListener {
                                        viewModel.deleteNote(item)
                                        requireContext().showSnackBar(
                                            rootView = binding.root,
                                            message = "Deleted",
                                            anchorView = binding.btnAddTasks,
                                            actionText = "Undo",
                                            onAction = { undoDelete(item) }
                                        )
                                    }
                                }
                            }
                            if (MainActivity.isAnimatedRecyclerView){
                                val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)
                                binding.rvTasks.layoutAnimation = controller
                                binding.rvTasks.scheduleLayoutAnimation()
                                MainActivity.isAnimatedRecyclerView = false
                            }
                            //for return animation
                            postponeEnterTransition()
                            view.viewTreeObserver.addOnPreDrawListener {
                                startPostponedEnterTransition()
                                true
                            }
                            enableSortCheckBox(true)
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        requireContext().showSnackBar(
                            rootView = binding.root,
                            message = data.message.toString(),
                            anchorView = binding.btnAddTasks,
                        )
                    }
                }
            }
        }

        binding.btnAddTasks.setOnClickListener {
            findNavController().navigate(R.id.action_allTasksFragment_to_addTaskFragment)
        }

        binding.checkboxSort.setOnClickListener {
            val isChecked = binding.checkboxSort.isChecked
            if (isChecked) {
                viewModel.getNotes(true)
                saveSortCheckBoxState(true)
            } else {
                viewModel.getNotes()
                saveSortCheckBoxState(false)
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvTasks.layoutManager = LinearLayoutManager(activity)
    }

    private fun hideProgressBar() {
        binding.progressbar.visibility = GONE
    }

    private fun showProgressBar() {
        binding.progressbar.visibility = VISIBLE
    }

    private fun saveSortCheckBoxState(isChecked: Boolean) {
        viewModel.saveSortCheckBoxState(isChecked)
    }

    private fun enableSortCheckBox(isEnabled: Boolean) {
        binding.checkboxSort.isEnabled = isEnabled
    }

    private fun undoDelete(note: NoteEntity) {
        executeInCoroutine {
            addNotesVM.saveNote(note)
        }
    }
}
