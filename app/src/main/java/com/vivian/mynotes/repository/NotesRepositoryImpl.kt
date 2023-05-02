package com.vivian.mynotes.repository

import com.vivian.mynotes.db.NotesDao
import com.vivian.mynotes.models.NoteEntity
import com.vivian.mynotes.prefstore.NoteDatastore
import com.vivian.mynotes.prefstore.NoteDatastoreImpl
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val dao: NotesDao,
    private val datastore: NoteDatastore,
) : NotesRepository {

    override suspend fun saveNote(note: NoteEntity) = dao.saveNote(note)
    override fun getAllNotes() = dao.getNotes()
    override suspend fun deleteNote(note: NoteEntity) = dao.deleteNote(note)
    override suspend fun saveSortCheckBoxState(checkBoxState: Boolean) = datastore.setValue(
        NoteDatastoreImpl.IS_SORTED_KEY,
        checkBoxState
    )

    override suspend fun getSortCheckBoxState() = datastore.isNotesSorted.firstOrNull() ?: false
}
