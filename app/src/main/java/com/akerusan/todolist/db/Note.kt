package com.akerusan.todolist.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "note_db")
data class Note(
    var title: String,
    var description: String,
    var date: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}