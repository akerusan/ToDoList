package com.akerusan.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_db")
data class Note(
    var title: String,
    var description: String,
    var priority: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}