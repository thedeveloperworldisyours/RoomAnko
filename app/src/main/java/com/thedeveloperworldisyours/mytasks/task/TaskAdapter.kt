package com.thedeveloperworldisyours.mytasks.task

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thedeveloperworldisyours.mytasks.R
import com.thedeveloperworldisyours.mytasks.database.Task
import kotlinx.android.synthetic.main.task_item.view.*

class TaskAdapter(
        val tasks: List<Task>,
        val checkTask: (Task) -> Unit,
        val deleteTask: (Task) -> Unit) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tasks[position]
        holder.bind(item, checkTask, deleteTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.task_item, parent, false))
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(task: Task, checkTask: (Task) -> Unit, deleteTask: (Task) -> Unit) = with(itemView){
            task_item_textView.text = task.description
            task_item_done_checkBox.isChecked = task.completed
            task_item_done_checkBox.setOnClickListener{checkTask(task)}
            setOnClickListener { deleteTask(task) }
        }
    }
}