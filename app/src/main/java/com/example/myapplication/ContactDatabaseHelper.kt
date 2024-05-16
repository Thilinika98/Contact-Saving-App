package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactDatabaseHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "contactApp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allContact"
        private const val COLUMN_ID = "id"
        private const val COLUMN_FName = "fName"
        private const val COLUMN_LName = "lName"
        private const val COLUMN_Phone = "phone"
        private const val COLUMN_Disc = "disc"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_FName TEXT, $COLUMN_LName TEXT, $COLUMN_Phone INTEGER, $COLUMN_Disc TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(contact: Contact){
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_FName,contact.fName)
            put(COLUMN_LName,contact.lName)
            put(COLUMN_Phone,contact.phone)
            put(COLUMN_Disc,contact.disc)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllContact(): List<Contact>{
        val notesList = mutableListOf<Contact>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val fName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FName))
            val lName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LName))
            val phone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_Phone))
            val disc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Disc))

            val contact = Contact(id, fName, lName, phone, disc)
            notesList.add(contact)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateNote(contact: Contact){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_FName, contact.fName)
            put(COLUMN_LName,contact.lName)
            put(COLUMN_Phone,contact.phone)
            put(COLUMN_Disc,contact.disc)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(contact.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getNoteByID(noteId: Int): Contact{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val fName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FName))
        val lName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LName))
        val phone = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_Phone))
        val disc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Disc))

        cursor.close()
        db.close()
        return Contact(id, fName, lName, phone, disc)
    }

    fun deleteNote(noteId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }



}