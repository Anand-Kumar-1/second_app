package com.example.employeedetails

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.COLUMN_CONTACT
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.COLUMN_EMAIL
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.COLUMN_NAME
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.ID
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.TABLE_NAME
import com.example.employeedetails.data.EmployeeDBHelper
import kotlinx.android.synthetic.main.activity_entry.*

class EntryActivity : AppCompatActivity() {

    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        id = intent.getIntExtra("IDofRow",0)
        if(id!= 0){
            //Log.d("Anand","id is $id")
            readInfo(id)
        }
    }
    private fun readInfo(id : Int){
        val mDBHelper = EmployeeDBHelper(this)
        val db = mDBHelper.readableDatabase

        val projection = arrayOf(COLUMN_NAME, COLUMN_CONTACT, COLUMN_EMAIL)

        val selection = "$ID = ?"
        val selectionArgs = arrayOf("$id")
        //Log.d("Anand","msg $selection")
        val cursor : Cursor = db.query(TABLE_NAME, projection, selection, selectionArgs,null,null,null)

        val nameColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME)
        val contactColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_CONTACT)
        val emailColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_EMAIL)

        while (cursor.moveToNext()){
            val currentName = cursor.getString(nameColumnIndex)
            val currentContact = cursor.getString(contactColumnIndex)
            val currentEmail = cursor.getString(emailColumnIndex)

            employee_name.setText(currentName)
            employee_contact.setText(currentContact)
            employee_email.setText(currentEmail)
        }
        cursor.close()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.action_bar_menu,menu)
        return true
    }

    private fun insertEntry(){
        val nameString  = employee_name.text.toString().trim(){it <= ' '}
        val emailString = employee_email.text.toString().trim(){it <= ' '}
        val contactString = employee_contact.text.toString().trim(){it <= ' '}

        val mDBHelper = EmployeeDBHelper(this)

        val db = mDBHelper.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME,nameString)
            put(COLUMN_CONTACT,emailString)
            put(COLUMN_EMAIL,contactString)
        }
        val rowId = db.insert(TABLE_NAME,null,values)

        if(rowId == (-1).toLong()){
            Toast.makeText(this, "problem in inserting new entry", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "entry recorded successfully with RowId $rowId", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateDiary(id : Int){
        val mDBHelper = EmployeeDBHelper(this)
        val db = mDBHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME,employee_name.text.toString())
            put(COLUMN_CONTACT,employee_contact.text.toString())
            put(COLUMN_EMAIL,employee_email.text.toString())
        }
        db.update(TABLE_NAME,values,"$ID = $id",null)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.save_entry ->{
                if(id == 0) {
                    insertEntry()
                }else{
                    updateDiary(id)
                }
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}