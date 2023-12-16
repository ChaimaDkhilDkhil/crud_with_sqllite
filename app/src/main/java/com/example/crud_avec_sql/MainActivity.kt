package com.example.crud_avec_sql

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button
    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var selectedStudent: StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addStudent() }
        btnView.setOnClickListener { getStudent() }
        btnUpdate.setOnClickListener { updateStudent() }

        adapter?.setOnClickItem { student ->
            Toast.makeText(this, student!!.name, Toast.LENGTH_SHORT).show()
            if (student != null) {
                edName.setText(student.name)
            }
            edEmail.setText(student.email)
            selectedStudent = student
        }

        adapter?.setOnClickDeleteItem { student ->
            deleteStudent(student!!.id)
        }
    }

    private fun getStudent() {
        val studentList = sqliteHelper.getAllStudent()
        Log.e("pppp", "${studentList.size}")
        adapter?.addItems(studentList)
    }

    private fun addStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter required fields", Toast.LENGTH_SHORT).show()
        } else {
            val student = StudentModel(name = name, email = email)
            val status = sqliteHelper.insertStudent(student)

            if (status > -1) {
                Toast.makeText(this, "Student Added...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudent()
            } else {
                Toast.makeText(this, "Record Not Saved...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText() {
        edName.setText("")
        edEmail.setText("")
    }

    private fun updateStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name == selectedStudent?.name && email == selectedStudent?.email) {
            Toast.makeText(this, "Record not changed...", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedStudent == null) return

        val updatedStudent = StudentModel(id = selectedStudent!!.id, name = name, email = email)
        val status = sqliteHelper.updateStudent(updatedStudent)

        if (status > -1) {
            clearEditText()
            getStudent()
            Toast.makeText(this, "Record updated successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteStudent(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this item?")
        builder.setCancelable(true)

        builder.setPositiveButton("Yes") { dialog, _ ->
            sqliteHelper.deleteStudentById(id)
            getStudent()
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }

        val alert = builder.create()
        alert.show()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}
