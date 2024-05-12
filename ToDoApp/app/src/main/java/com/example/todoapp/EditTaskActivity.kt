package com.example.todoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.Model.Task
import com.example.todoapp.Utils.TaskViewModel

class EditTaskActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var priorityEditText: EditText
    private lateinit var deadlineEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var editedTask: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_task)

        // Initialize ViewModel
        taskViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(TaskViewModel::class.java)

        // Find views from the layout
        nameEditText = findViewById(R.id.edit_name)
        descriptionEditText = findViewById(R.id.edit_description)
        priorityEditText = findViewById(R.id.edit_priority)
        deadlineEditText = findViewById(R.id.edit_deadline)
        saveButton = findViewById(R.id.saveEditButton)

        // Retrieve task details from the intent
        editedTask = intent.getParcelableExtra("task")!!
        // Populate EditText fields with task details
        nameEditText.setText(editedTask.name)
        descriptionEditText.setText(editedTask.description)
        priorityEditText.setText(editedTask.priority.toString())
        deadlineEditText.setText(editedTask.deadline.toString())

        // Set up click listener for the save button
        saveButton.setOnClickListener {
            saveEditedTask()
        }
    }

    private fun saveEditedTask() {
        // Retrieve edited values from EditText fields
        val editedName = nameEditText.text.toString().trim()
        val editedDescription = descriptionEditText.text.toString().trim()
        val editedPriority = priorityEditText.text.toString().trim().toInt()
        val editedDeadline = deadlineEditText.text.toString().trim().toLong()

        // Update the edited task with new values
        editedTask.apply {
            name = editedName
            description = editedDescription
            priority = editedPriority
            deadline = editedDeadline
        }

        // Update the task in the database using ViewModel
        taskViewModel.updateTask(editedTask)

        // Finish the activity
        finish()
    }
}
