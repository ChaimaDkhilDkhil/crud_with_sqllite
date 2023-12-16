package com.example.crud_avec_sql

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private var studentList: ArrayList<StudentModel> = ArrayList()
    private var onClickItem: ((StudentModel?) -> Unit)? = null
    private var onClickDeleteItem: ((StudentModel?) -> Unit)? = null

    fun addItems(items: ArrayList<StudentModel>) {
        this.studentList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (StudentModel?) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (StudentModel?) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_items_std, parent, false),
            onClickItem,
            onClickDeleteItem
        )
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = studentList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener { onClickItem?.invoke(std) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(std) }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    class StudentViewHolder(
        view: View,
        private val onClickItem: ((StudentModel?) -> Unit)?,
        private val onClickDeleteItem: ((StudentModel?) -> Unit)?
    ) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.tvName)
        private val email = view.findViewById<TextView>(R.id.tvEmail)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std: StudentModel) {
            name.text = std.name
            email.text = std.email
        }
    }
}
