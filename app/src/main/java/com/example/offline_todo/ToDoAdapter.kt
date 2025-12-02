package com.example.offline_todo

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter(var todolist: List<TodoList>, var context: Context) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    private val db: DatabaseHelper = DatabaseHelper(context)

    class ToDoViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var title = itemView.findViewById<TextView>(R.id.noteTitle)
        var description = itemView.findViewById<TextView>(R.id.noteDes)
        var date = itemView.findViewById<TextView>(R.id.noteDate)
        var imgDelete = itemView.findViewById<ImageView>(R.id.imgDelete)
        var imgEdit = itemView.findViewById<ImageView>(R.id.imgEdit)
    }
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): ToDoAdapter.ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_notelist, parent, false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoAdapter.ToDoViewHolder, position: Int) {
        val todo = todolist[position]
        holder.title.text = todo.title
        holder.description.text = todo.description
        holder.date.text = todo.date
        holder.imgEdit.setOnClickListener {

        }
        holder.imgDelete.setOnClickListener {
            val bulder = AlertDialog.Builder(context)
            bulder.setTitle("Delete")
            bulder.setMessage("Are you sure you want to delete this item?")
            bulder.setPositiveButton("OK") { dialog, which ->
                db.deleteDate(todo.id)
                refreshData(db.getAllData())
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()

            }
            bulder.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            val dialog : AlertDialog = bulder.create()
            dialog.show()

        }


    }

    override fun getItemCount(): Int {
        return todolist.size
    }

    fun refreshData(newNote: List<TodoList>) {
        todolist = newNote
        notifyDataSetChanged()
    }
}