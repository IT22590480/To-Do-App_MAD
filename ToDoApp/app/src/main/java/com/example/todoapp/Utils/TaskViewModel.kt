package com.example.todoapp.Utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.Model.Task

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    val allTasks: LiveData<List<Task>> = taskRepository.getAllTasksLiveData()

    // Zero-argument constructor (not needed in Kotlin)
    // Init block to handle initialization if necessary

    fun getAllTasksSync(): List<Task>? {
        return taskRepository.getAllTasksSync()
    }

    fun insertTask(task: Task) {
        taskRepository.insertTask(task)
    }

    fun updateTask(task: Task) {
        taskRepository.updateTask(task)
    }

    fun deleteTask(task: Task) {
        taskRepository.deleteTask(task)
    }
}
