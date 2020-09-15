package com.example.employeedetails.data

import android.provider.BaseColumns

object DatabaseManager {

    object EmployeeEntry : BaseColumns{
        const val TABLE_NAME = "employees"
        const val ID = BaseColumns._ID
        const val COLUMN_NAME ="name"
        const val COLUMN_CONTACT = "contact"
        const val COLUMN_EMAIL = "email"
    }
}