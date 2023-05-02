package com.vivian.mynotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.vivian.mynotes.databinding.ItemNoteBinding
import com.vivian.mynotes.models.NoteEntity
import com.vivian.mynotes.ui.AllNotesFragmentDirections

class NotesAdapter(
    private val notes: List<NoteEntity>,
    private val onItemClickListener: (ItemNoteBinding, NoteEntity) -> Unit = { _: ItemNoteBinding, _: NoteEntity -> }
) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {

        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(createOnClickListener(holder.binding, notes[position]), notes[position])
    }

    override fun getItemCount() = notes.size

    inner class NotesViewHolder(val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: NoteEntity) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description

            ViewCompat.setTransitionName(binding.tvTitle, "title_${item.id}")
            ViewCompat.setTransitionName(binding.tvDescription, "description_${item.id}")
            ViewCompat.setTransitionName(binding.tvDeadline, "deadline_${item.id}")

            if (item.deadLine != "0")
                binding.tvDeadline.text = item.deadLine

            binding.root.setOnClickListener(listener)
            onItemClickListener(binding, item)
        }
    }

    private fun createOnClickListener(
        binding: ItemNoteBinding,
        task: NoteEntity
    ): View.OnClickListener {
        return View.OnClickListener {
            val directions =
                AllNotesFragmentDirections.actionAllTasksFragmentToAddTaskFragment(task)
            val extras = FragmentNavigatorExtras(
                binding.tvTitle to "title_${task.id}",
                binding.tvDescription to "description_${task.id}",
                binding.tvDeadline to "deadline_${task.id}"
            )
            it.findNavController().navigate(directions, extras)
        }
    }
}