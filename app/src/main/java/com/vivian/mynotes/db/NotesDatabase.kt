package com.vivian.mynotes.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.vivian.mynotes.models.NoteEntity

@Database(
    entities = [NoteEntity::class],
    version = 3,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun dao(): NotesDao

}