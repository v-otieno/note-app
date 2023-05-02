package com.vivian.mynotes.repository

import com.vivian.mynotes.models.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun saveNote(note: NoteEntity)

    fun getAllNotes(): Flow<List<NoteEntity>>

    suspend fun deleteNote(note: NoteEntity)

    suspend fun saveSortCheckBoxState(checkBoxState: Boolean)

    suspend fun getSortCheckBoxState(): Boolean
}