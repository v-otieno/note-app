package com.vivian.mynotes.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by Vivian on 30/04/23.
 */
object Migrations {

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // add temporary table
            database.execSQL(
                "CREATE TABLE notes_temp (id INTEGER, title TEXT NOT NULL, description TEXT NOT NULL,deadLine TEXT NOT NULL DEFAULT '0', PRIMARY KEY(id))"
            )
            // copy data to new table
            database.execSQL(
                "INSERT INTO notes_temp (id, title, description,deadLine) SELECT id, title,description,deadLine FROM notes"
            )
            // remove the old table
            database.execSQL("DROP TABLE notes")
            // rename temporary to replace old table
            database.execSQL("ALTER TABLE notes_temp RENAME TO notes")
        }
    }
}