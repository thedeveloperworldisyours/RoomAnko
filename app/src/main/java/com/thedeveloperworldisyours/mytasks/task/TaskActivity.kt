package com.thedeveloperworldisyours.mytasks.task

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import com.thedeveloperworldisyours.mytasks.MyTasksApp
import com.thedeveloperworldisyours.mytasks.R
import com.thedeveloperworldisyours.mytasks.database.Task
import kotlinx.android.synthetic.main.task_activity.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TaskActivity : AppCompatActivity() {

    lateinit var adapter: TaskAdapter
    lateinit var tasks: MutableList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_activity)
        tasks = ArrayList()
        getTasks()
        task_button.setOnClickListener {
            addTask(Task(description = task_editText.text.toString()))}
    }

    fun clearFocus(){
        task_editText.setText("")
    }

    fun Context.hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    fun getTasks() {
        doAsync {
            tasks = MyTasksApp.database.taskDao().getAllTasks()
            uiThread {
                setUpRecyclerView(tasks)
            }
        }
    }

    fun addTask(task: Task){
        doAsync {
            val idTask = MyTasksApp.database.taskDao().insertTask(task)
            val recoveryTask = MyTasksApp.database.taskDao().findTaskById(idTask)
            uiThread {
                tasks.add(recoveryTask)
                adapter.notifyItemInserted(tasks.size)
                clearFocus()
                hideKeyboard()
            }
        }
    }

    fun updateTask(task: Task) {
        doAsync {
            task.completed = !task.completed
            MyTasksApp.database.taskDao().updateTask(task)
        }
    }

    fun deleteTask(task: Task){
        doAsync {
            val position = tasks.indexOf(task)
            MyTasksApp.database.taskDao().deleteTask(task)
            tasks.remove(task)
            uiThread {
                //                toast("delete ${tasks[position].name}")
                adapter.notifyItemRemoved(position)
            }
        }
    }

    fun setUpRecyclerView(tasks: List<Task>) {
        adapter = TaskAdapter(tasks, { updateTask(it) }, {deleteTask(it)})
        task_recyclerView.setHasFixedSize(true)
        task_recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        task_recyclerView.adapter = adapter
    }
}
