package com.example.employeedetails.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.COLUMN_CONTACT
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.COLUMN_EMAIL
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.COLUMN_NAME
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.ID
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.TABLE_NAME
import java.sql.SQLClientInfoException

class EmployeeDBHelper(context :Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val  DATABASE_NAME = "employees.db"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_DIARY_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, "+
                "$COLUMN_CONTACT TEXT, "+
                "$COLUMN_EMAIL TEXT )"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_DIARY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_ENTRIES)
    }
}