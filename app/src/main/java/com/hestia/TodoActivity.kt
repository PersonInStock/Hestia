package com.hestia

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.hestia.database.MyDbHelper
import com.hestia.databinding.ActivityTodoBinding
import com.hestia.databinding.ListItemBinding
import kotlin.collections.ArrayList


class TodoActivity : AppCompatActivity() {

    var tasks = arrayListOf<TasksTable.Task>()
    var dbHelper = MyDbHelper(this)
    lateinit var tasksDb: SQLiteDatabase
    lateinit var taskAdapter: TaskAdapter

    private lateinit var binding: ActivityTodoBinding
    private lateinit var binding1: ListItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoBinding.inflate(layoutInflater)
        binding1 = ListItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancelAll()

        tasksDb = dbHelper.writableDatabase

        tasks = TasksTable.getAllTasks(tasksDb)
        taskAdapter = TaskAdapter(tasks)

        binding.todoView.adapter = taskAdapter

        binding.btnAdd.setOnClickListener {
            val data =  binding.eText.text.toString()
            if (data == "") {
                Toast.makeText(this, "Please enter your todo!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            TasksTable.insertTask(
                tasksDb,
                TasksTable.Task(
                    null,
                    binding.eText.text.toString(),
                    false
                )
            )
            tasks = TasksTable.getAllTasks(tasksDb)
            taskAdapter.updateTasks(tasks)
            binding.eText.setText("")
        }


        binding.btnDelet.setOnClickListener {
            TasksTable.deletTask(tasksDb)
            tasks = TasksTable.getAllTasks(tasksDb)
            taskAdapter.updateTasks(tasks)
        }

        binding.eTextS.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, end: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, end: Int) {
                if (s == "") {
                    tasks = TasksTable.getAllTasks(tasksDb)
                    taskAdapter.updateTasks(tasks)
                } else {
                    tasks = TasksTable.search(tasksDb, s.toString())
                    Log.d("Task", "TASK : $tasks")
                    taskAdapter.updateTasks(tasks)
                }
            }
        })

        binding.todoView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val thisTask = taskAdapter.getItem(position)
                thisTask.done = !thisTask.done
                binding1.check.isChecked = thisTask.done
                TasksTable.updateTask(tasksDb, thisTask)
                tasks = TasksTable.getAllTasks(tasksDb)
                taskAdapter.updateTasks(tasks)
            }

        assert(
            supportActionBar != null //null check
        )
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //show back button
        supportActionBar!!.title = "To Do List";

    }

    inner class TaskAdapter(var tasksO: ArrayList<TasksTable.Task>) : BaseAdapter() {

        fun updateTasks(newTasks: ArrayList<TasksTable.Task>) {
            tasksO.clear()
            tasksO.addAll(newTasks)
            notifyDataSetChanged()
        }

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val li =
                parent!!.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = li.inflate(R.layout.list_item, parent, false)
            view.findViewById<TextView>(R.id.tView).text = getItem(position).task
            if (getItem(position).done) {
                view.findViewById<TextView>(R.id.tView).setTextColor(Color.GRAY)
                view.findViewById<CheckBox>(R.id.check).isChecked = true
            } else {
                view.findViewById<TextView>(R.id.tView).setTextColor(Color.BLACK)
                view.findViewById<CheckBox>(R.id.check).isChecked = false
            }

            view.findViewById<CheckBox>(R.id.check).setOnClickListener {
                val thisTask = taskAdapter.getItem(position)
                thisTask.done = !thisTask.done
                binding1.check.isChecked = thisTask.done
                TasksTable.updateTask(tasksDb, thisTask)
                tasks = TasksTable.getAllTasks(tasksDb)
                taskAdapter.updateTasks(tasks)
            }

            return view
        }

        override fun getItem(position: Int): TasksTable.Task = tasks[position]
        override fun getItemId(position: Int): Long = 0
        override fun getCount(): Int = tasks.size

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


}


