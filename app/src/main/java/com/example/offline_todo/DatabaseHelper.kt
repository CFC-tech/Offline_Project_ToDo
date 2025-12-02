package com.example.offline_todo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ( $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT, $COLUMN_DATE TEXT, $COLUMN_TIME TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertData(todo: TodoList): Long {
        val values = ContentValues().apply {
            put(COLUMN_TITLE, todo.title)
            put(COLUMN_DESCRIPTION, todo.description)
            put(COLUMN_DATE, todo.date)
            put(COLUMN_TIME, todo.time)
        }
        return writableDatabase.use { db ->
            db.insert(TABLE_NAME, null, values)
        }
    }

    fun getAllData(): List<TodoList> {
        val notelist = mutableListOf<TodoList>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        while ( cursor.moveToNext() ) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
            val todo = TodoList(id, title, description, date, time)
            notelist.add(todo)
        }
        cursor.close()
        db.close()
        return notelist

    }
    fun deleteDate(noteID: Int){
        val db = writableDatabase
        val whereClass = "$COLUMN_ID=?"
        val whereArgs = arrayOf(noteID.toString())
        db.delete(TABLE_NAME, whereClass, whereArgs)
        db.close()
    }

    fun getNoteById(noteId: Int): TodoList{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
        val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
        val time = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME))
        val note = TodoList(id, title, description, date, time)
        db.close()
        return note
    }
    fun updateData(todo: TodoList){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, todo.title)
            put(COLUMN_DESCRIPTION, todo.description)
            put(COLUMN_DATE, todo.date)
            put(COLUMN_TIME, todo.time)
        }
        val whereClass = "$COLUMN_ID=?"
        val whereArgs = arrayOf(todo.id.toString())
        db.update(TABLE_NAME, values, whereClass, whereArgs)
        db.close()
    }
}
