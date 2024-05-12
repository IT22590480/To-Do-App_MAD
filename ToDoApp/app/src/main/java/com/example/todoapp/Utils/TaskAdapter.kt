package com.example.todoapp.Utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Model.Task
import com.example.todoapp.R
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(private val context: Context) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var tasks: List<Task>? = null
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setTasks(tasks: List<Task>?) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    fun editItem(position: Int) {
        // Implement your logic to handle editing here
        // For example, you can open an edit activity with the details of the task
        if (listener != null) {
            listener!!.onItemClick(position)
        }
    }

    fun getTaskAtPosition(position: Int): Task? {
        return if (tasks != null && position >= 0 && position < tasks!!.size) {
            tasks!![position]
        } else null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks!![position]
        holder.taskNameTextView.text = task.name
        holder.taskDescriptionTextView.text = task.description
        holder.taskPriorityTextView.text = task.priority.toString()
        holder.taskDeadlineTextView.text = dateFormat.format(Date(task.deadline))
        holder.taskCheckbox.isChecked = task.status
    }

    override fun getItemCount(): Int {
        return tasks?.size ?: 0
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskNameTextView: TextView = itemView.findViewById(R.id.taskNameTextView)
        val taskDescriptionTextView: TextView = itemView.findViewById(R.id.taskDescriptionTextView)
        val taskPriorityTextView: TextView = itemView.findViewById(R.id.taskPriorityTextView)
        val taskDeadlineTextView: TextView = itemView.findViewById(R.id.taskDeadlineTextView)
        val taskCheckbox: CheckBox = itemView.findViewById(R.id.taskCheckbox)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(position)
                }
            }
        }
    }
}
