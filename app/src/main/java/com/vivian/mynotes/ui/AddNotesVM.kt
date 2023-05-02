package com.vivian.mynotes.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivian.mynotes.models.NoteEntity
import com.vivian.mynotes.repository.NotesRepository
import com.vivian.mynotes.utils.Constants.KEY_SAVED_STATE_DEADLINE
import com.vivian.mynotes.utils.dispatchers.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNotesVM @Inject constructor(
    private val repository: NotesRepository,
    private val dispatchers: DispatchersProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    suspend fun saveNote(note: NoteEntity) {
        viewModelScope.launch(dispatchers.main) {
            repository.saveNote(note)
        }
    }

    fun fetchDeadline() = savedStateHandle.get<String>(KEY_SAVED_STATE_DEADLINE)

    fun setDeadline(date: String) {
        savedStateHandle[KEY_SAVED_STATE_DEADLINE] = date
    }
}