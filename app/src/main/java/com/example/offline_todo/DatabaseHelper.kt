package com.example.offline_todo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    companion object {
        const val DATABASE_NAME = "todo.db"
        const val TABLE_NAME = "todo_table"
        const val DATABASE_VERSION = 1
        const val COLUMN_ID = "ID"
        const val COLUMN_TITLE = "TITLE"
        const val COLUMN_DESCRIPTION = "DESCRIPTION"
        const val COLUMN_DATE = "DATE"
        const val COLUMN_TIME = "TIME"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_DESCRIPTION TEXT, " +
                "$COLUMN_DATE TEXT, " +
                "$COLUMN_TIME TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertData(todo : TodoList) {
        val db = writableDatabase
        val values = android.content.ContentValues().apply {
            put(COLUMN_TITLE, todo.title)
            put(COLUMN_DESCRIPTION, todo.description)
            put(COLUMN_DATE, todo.date)
            put(COLUMN_TIME, todo.time)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
}