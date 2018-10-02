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
import com.thedeveloperworldisyours.mytasks.database.TaskEntity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TaskActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: TaskAdapter
    lateinit var tasks: MutableList<TaskEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tasks = ArrayList()
        getTasks()
        btnAddTask.setOnClickListener {
            addTask(TaskEntity(name = etTask.text.toString()))}
    }

    fun clearFocus(){
        etTask.setText("")
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

    fun addTask(task:TaskEntity){
        doAsync {
            val id = MyTasksApp.database.taskDao().addTask(task)
            val recoveryTask = MyTasksApp.database.taskDao().getTaskById(id)
            uiThread {
                tasks.add(recoveryTask)
                adapter.notifyItemInserted(tasks.size)
                clearFocus()
                hideKeyboard()
            }
        }
    }

    fun updateTask(task: TaskEntity) {
        doAsync {
            task.isDone = !task.isDone
            MyTasksApp.database.taskDao().updateTask(task)
        }
    }

    fun deleteTask(task: TaskEntity){
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

    fun setUpRecyclerView(tasks: List<TaskEntity>) {
        adapter = TaskAdapter(tasks, { updateTask(it) }, {deleteTask(it)})
        recyclerView = findViewById(R.id.rvTask)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
