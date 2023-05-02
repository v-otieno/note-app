package com.vivian.mynotes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivian.mynotes.models.NoteEntity
import com.vivian.mynotes.repository.NotesRepository
import com.vivian.mynotes.utils.DateTimeUtils.toTime
import com.vivian.mynotes.utils.Resource
import com.vivian.mynotes.utils.dispatchers.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllNotesVM @Inject constructor(
    private val repository: NotesRepository,
    private val dispatchers: DispatchersProvider,
) : ViewModel() {

    private val _notes = MutableLiveData<Resource<List<NoteEntity>>>()
    val notes: LiveData<Resource<List<NoteEntity>>> = _notes
    private val _checkBoxState = MutableLiveData<Boolean>()
    val checkBoxState: LiveData<Boolean> = _checkBoxState

    fun getNotes(sort: Boolean = false) {
        viewModelScope.launch(dispatchers.main) {
            _notes.postValue(Resource.Loading())
            repository.getAllNotes().collect {
                if (sort)
                    _notes.value = Resource.Success(sortNotes(it))
                else
                    _notes.value = Resource.Success(it)
            }
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch(dispatchers.main) {
            repository.deleteNote(note)
        }
    }

    private fun sortNotes(notes: List<NoteEntity>): List<NoteEntity> {
        return notes.sortedBy {
            it.deadLine.toTime()
        }
    }

    fun saveSortCheckBoxState(isChecked: Boolean) {
        viewModelScope.launch(dispatchers.main) {
            repository.saveSortCheckBoxState(isChecked)
        }
    }

    fun getSortCheckBoxState() {
        viewModelScope.launch(dispatchers.main) {
            _checkBoxState.value = repository.getSortCheckBoxState()
        }
    }

    fun clear() {
        this.onCleared()
    }
}
