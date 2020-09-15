package com.example.employeedetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
    }
    fun createNewEntry(view : View){
        val intent = Intent(this,EntryActivity::class.java)
        startActivity(intent)
    }
    fun fetchEntries(view : View){
        val intentTwo = Intent(this,EmployeeListActivity::class.java)
        startActivity(intentTwo)
    }
}



