package com.example.todoapp.Utils

import androidx.lifecycle.LiveData
import com.example.todoapp.Model.Task

class TaskRepository(private val taskDao: TaskDao) {

    fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    fun updateTask(task: Task) {
        taskDao.update(task)
    }

    fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

    fun getAllTasksLiveData(): LiveData<List<Task>> {
        return taskDao.getAllTasks()
    }

    fun getTaskByIdLiveData(taskId: Long): LiveData<Task> {
        return taskDao.getTaskById(taskId)
    }

    fun getAllTasksSync(): List<Task> {
        return taskDao.getAllTasksSync()
    }
}
