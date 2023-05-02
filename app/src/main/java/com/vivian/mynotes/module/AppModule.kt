package com.vivian.mynotes.di.module

import android.content.Context
import androidx.room.Room
import com.vivian.mynotes.db.Migrations.MIGRATION_2_3
import com.vivian.mynotes.db.NotesDao
import com.vivian.mynotes.db.NotesDatabase
import com.vivian.mynotes.prefstore.NoteDatastore
import com.vivian.mynotes.prefstore.NoteDatastoreImpl
import com.vivian.mynotes.repository.NotesRepository
import com.vivian.mynotes.repository.NotesRepositoryImpl
import com.vivian.mynotes.utils.dispatchers.DefaultDispatchers
import com.vivian.mynotes.utils.dispatchers.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        NotesDatabase::class.java,
        "notes.db"
    ).addMigrations(MIGRATION_2_3)
        .build()

    @Singleton
    @Provides
    fun provideNoteDao(database: NotesDatabase) = database.dao()

    @Singleton
    @Provides
    fun provideNoteRepository(dao: NotesDao, datastore: NoteDatastore): NotesRepository {
        return NotesRepositoryImpl(dao, datastore)
    }

    @Singleton
    @Provides
    fun provideNoteDataStore(@ApplicationContext context: Context): NoteDatastore {
        return NoteDatastoreImpl(context)
    }

    @Singleton
    @Provides
    fun provideDispatchersProvider(): DispatchersProvider {
        return DefaultDispatchers()
    }
}