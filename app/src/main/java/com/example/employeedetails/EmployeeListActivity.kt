package com.example.employeedetails

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.COLUMN_CONTACT
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.COLUMN_EMAIL
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.COLUMN_NAME
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.ID
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.TABLE_NAME
import com.example.employeedetails.data.Employee
import com.example.employeedetails.data.EmployeeDBHelper
import kotlinx.android.synthetic.main.activity_employee_list.*

class EmployeeListActivity : AppCompatActivity() {

    private var employeeList : ArrayList<Employee> = ArrayList()
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var adapter : EmployeeAdapter

    private lateinit var mDBHelper : EmployeeDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)

        mDBHelper = EmployeeDBHelper(this)

        displayDataInfo()
    }
    private fun displayDataInfo(){

        val db = mDBHelper.readableDatabase

        val projection = arrayOf(ID, COLUMN_NAME, COLUMN_CONTACT, COLUMN_EMAIL)

        val cursor : Cursor = db.query(TABLE_NAME, projection,null,null,null,null,null)
        val idColumnIndex = cursor.getColumnIndexOrThrow(ID)
        val nameColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME)
        val contactColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_CONTACT)
        val emailColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_EMAIL)

        while (cursor.moveToNext()){
            val currentId = cursor.getInt(idColumnIndex)
            val currentName = cursor.getString(nameColumnIndex)
            val currentContact = cursor.getString(contactColumnIndex)
            val currentEmail = cursor.getString(emailColumnIndex)
            employeeList.add(Employee(currentId,currentName,currentContact,currentEmail))
        }
        cursor.close()
        linearLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayoutManager
        adapter = EmployeeAdapter(employeeList)
        recycler_view.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        employeeList.clear()
        displayDataInfo()
    }
}