package com.example.todoapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.Utils.TaskAdapter
import com.example.todoapp.Utils.TaskViewModel

class RecyclerItemTouchHelper(
    private val adapter: TaskAdapter,
    private val taskViewModel: TaskViewModel,
    private val context: Context
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder(context)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this Task?")
                .setPositiveButton("Confirm") { dialog, which ->
                    val taskToDelete = adapter.getTaskAtPosition(position)
                    taskToDelete?.let { taskViewModel.deleteTask(it) }
                }
                .setNegativeButton(android.R.string.cancel) { dialog, which ->
                    adapter.notifyItemChanged(viewHolder.adapterPosition)
                }
                .create()
                .show()
        } else {
            adapter.editItem(position)
        }
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val icon: Drawable?
        val background: ColorDrawable

        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20

        icon = if (dX > 0) {
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_edit)
        } else {
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete)
        }

        background = if (dX > 0) {
            ColorDrawable(ContextCompat.getColor(context, R.color.colorPrimaryPurple))
        } else {
            ColorDrawable(Color.RED)
        }

        icon?.let {
            val iconMargin = (itemView.height - it.intrinsicHeight) / 2
            val iconTop = itemView.top + (itemView.height - it.intrinsicHeight) / 2
            val iconBottom = iconTop + it.intrinsicHeight

            if (dX > 0) {
                val iconLeft = itemView.left + iconMargin
                val iconRight = itemView.left + iconMargin + it.intrinsicWidth
                it.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                background.setBounds(itemView.left, itemView.top,
                    itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom)
            } else if (dX < 0) {
                val iconLeft = itemView.right - iconMargin - it.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                it.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                background.setBounds(itemView.right + dX.toInt() - backgroundCornerOffset,
                    itemView.top, itemView.right, itemView.bottom)
            } else {
                background.setBounds(0, 0, 0, 0)
            }

            background.draw(c)
            it.draw(c)
        }
    }
}
