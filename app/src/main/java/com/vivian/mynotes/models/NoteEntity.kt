package com.vivian.mynotes.models

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Keep
@Parcelize
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val title: String,
    val description: String,
    @ColumnInfo(defaultValue = "0")
    val deadLine: String
) : Parcelable