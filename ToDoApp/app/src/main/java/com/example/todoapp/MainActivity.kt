package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.Model.Task
import com.example.todoapp.Utils.TaskAdapter
import com.example.todoapp.Utils.TaskDao
import com.example.todoapp.Utils.TaskDatabase
import com.example.todoapp.Utils.TaskRepository
import com.example.todoapp.Utils.TaskViewModel
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskAdapter.OnItemClickListener {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Initialize RecyclerView and TaskAdapter
        binding.tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(this)
        binding.tasksRecyclerView.adapter = taskAdapter

        // Create an instance of TaskDao
        val taskDao: TaskDao = TaskDatabase.getInstance(this).taskDao()

        // Create an instance of TaskRepository with TaskDao
        val taskRepository = TaskRepository(taskDao)

        // Pass the taskRepository to the TaskViewModel constructor
        taskViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(TaskViewModel::class.java)
        taskViewModel.init(taskRepository)

        // Observe changes in task list
        taskViewModel.allTasks.observe(this, Observer { tasks ->
            taskAdapter.setTasks(tasks)
        })

        Log.d("jeewa", "jeewantha")

        // Set click listener for FloatingActionButton to start AddNewTask activity
        binding.fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddNewTask::class.java))
        }

        // Attach ItemTouchHelper to RecyclerView for swipe-to-delete functionality
        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(taskAdapter, taskViewModel, this))
        itemTouchHelper.attachToRecyclerView(binding.tasksRecyclerView)
    }

    override fun onItemClick(position: Int) {
        // Get the task at the clicked position
        val clickedTask: Task? = taskAdapter.getTaskAtPosition(position)
        // Start the edit activity and pass the task details
        clickedTask?.let {
            val intent = Intent(this@MainActivity, EditTaskActivity::class.java)
            intent.putExtra("task", it)
            startActivity(intent)
        }
    }
}
