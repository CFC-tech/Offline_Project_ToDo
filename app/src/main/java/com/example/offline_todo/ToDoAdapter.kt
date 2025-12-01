package com.example.offline_todo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ToDoAdapter(var todolist: List<TodoList>, var context: Context) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {
    class ToDoViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var title = itemView.findViewById<TextView>(R.id.noteTitle)
        var description = itemView.findViewById<TextView>(R.id.noteDes)
        var date = itemView.findViewById<TextView>(R.id.noteDate)
        var imgDelete = itemView.findViewById<ImageView>(R.id.imgDelete)
        var imgEdit = itemView.findViewById<ImageView>(R.id.imgEdit)
    }
    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): ToDoAdapter.ToDoViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.todo_notelist, parent, false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoAdapter.ToDoViewHolder, position: Int) {
        var todo = todolist[position]
        holder.title.text = todo.title
        holder.description.text = todo.description
        holder.date.text = todo.date

    }

    override fun getItemCount(): Int {
        return todolist.size
    }

    fun refreshData(newNote: List<TodoList>) {
        todolist = newNote
        notifyDataSetChanged()
    }
}