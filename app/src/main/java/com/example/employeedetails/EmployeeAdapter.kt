package com.example.employeedetails

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.ID
import com.example.employeedetails.data.DatabaseManager.EmployeeEntry.TABLE_NAME
import com.example.employeedetails.data.Employee
import com.example.employeedetails.data.EmployeeDBHelper

class EmployeeAdapter(private var employeeList : MutableList<Employee>) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): EmployeeViewHolder {

        val context = viewGroup.context
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.recycler_item,viewGroup,false)

        passContext(context)
        return EmployeeViewHolder(view)
    }
    private fun passContext(context: Context): Context{
        return context

    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val item = employeeList[position]
        holder.bindEmployee(item)
        holder.delete.setOnClickListener{
            val mDBHelper = EmployeeDBHelper(holder.context)
            val db = mDBHelper.writableDatabase
            val selection = "$ID = ?"
            val selectionArgs = arrayOf("${(employeeList[position].id)}")

            db.delete(TABLE_NAME, selection, selectionArgs)

            employeeList.removeAt(position)
            notifyDataSetChanged()

        }

    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    class EmployeeViewHolder(v : View) : RecyclerView.ViewHolder(v),View.OnClickListener{
        private var view :View = v
        private lateinit var employee: Employee
        private var name : TextView
        private var contact : TextView
        private var email : TextView
        var delete : ImageButton
        var context : Context = itemView.context

        init{
            name = view.findViewById(R.id.name_recycler_item)
            contact = view.findViewById(R.id.contact_recycler_item)
            email = view.findViewById(R.id.email_recycler_item)
            delete = view.findViewById(R.id.delete_button)
            v.setOnClickListener(this)
        }
        fun bindEmployee(employee: Employee){
            this.employee = employee

            name.text = employee.name
            contact.text = employee.contact
            email.text = employee.email
        }

        override fun onClick(v: View?) {
            val context = itemView.context
            val intent = Intent(context,EntryActivity::class.java)
            intent.putExtra("IDofRow",employee.id)
            context.startActivity(intent)

        }
    }

}