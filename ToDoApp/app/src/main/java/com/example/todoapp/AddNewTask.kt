package com.example.todoapp

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.Model.Task
import com.example.todoapp.Utils.TaskDatabase
import com.example.todoapp.Utils.TaskViewModel

class AddNewTask : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var priorityEditText: EditText
    private lateinit var deadlineEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_task)

        // Initialize the TaskDatabase instance
        TaskDatabase.getInstance(this)

        // Initialize ViewModel
        taskViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(TaskViewModel::class.java)

        // Find views from the layout
        nameEditText = findViewById(R.id.name)
        descriptionEditText = findViewById(R.id.description)
        priorityEditText = findViewById(R.id.priority)
        deadlineEditText = findViewById(R.id.deadline)
        saveButton = findViewById(R.id.saveButton)

        // Set up click listener for the save button
        saveButton.setOnClickListener {
            saveTask()
        }
    }

    private fun saveTask() {
        val name = nameEditText.text.toString().trim()
        val description = descriptionEditText.text.toString().trim()
        val priority = priorityEditText.text.toString().trim().toInt()
        val deadline = deadlineEditText.text.toString().trim().toLong()

        // Create a new Task object
        val newTask = Task(name, description, priority, deadline, false)

        // Save the new task using the ViewModel
        taskViewModel.insertTask(newTask)

        // Log a message indicating that the task has been saved
        Log.d("AddNewTask", "Task saved: Title - ${newTask.name}, Description - ${newTask.description}, Deadline - ${newTask.deadline}")

        // Post a delayed Runnable to execute after 5 seconds
        Handler().postDelayed({
            // After the delay, retrieve tasks synchronously
            val tasks = taskViewModel.getAllTasksSync()

            // Check if tasks is not null
            tasks?.forEach { task ->
                // Log each task
                Log.d("AddNewTask", "Task retrieved: Title - ${task.name}, Description - ${task.description}, Deadline - ${task.deadline}")
            } ?: Log.d("AddNewTask", "No tasks found in the database")
        }, 5000) // 5000 milliseconds = 5 seconds

        // Finish the activity to go back to MainActivity
        finish()
    }
}
