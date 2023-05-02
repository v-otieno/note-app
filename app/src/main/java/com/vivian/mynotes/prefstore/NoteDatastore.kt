package com.vivian.mynotes.prefstore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface NoteDatastore {

    val isNotesSorted: Flow<Boolean>

    /**
     * Sets a value to a datastore key
     * */
    suspend fun <T> setValue(key: Preferences.Key<T>, value: T)
}