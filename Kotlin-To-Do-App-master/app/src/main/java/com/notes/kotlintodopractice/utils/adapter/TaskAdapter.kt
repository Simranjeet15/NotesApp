package com.notes.kotlintodopractice.utils.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notes.kotlintodopractice.databinding.EachTodoItemBinding
import com.notes.kotlintodopractice.utils.model.ToDoData

class TaskAdapter(private val list: MutableList<ToDoData>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private  val TAG = "TaskAdapter"
    private var listener: TaskAdapterInterface? = null // Listener for item click events

    // Method to set the listener for item click events
    fun setListener(listener: TaskAdapterInterface) {
        this.listener = listener
    }

    // ViewHolder for each task item
    class TaskViewHolder(val binding: EachTodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        // Inflate the layout for each task item
        val binding =
            EachTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.todoTask.text = this.task // Set task text to TextView

                Log.d(TAG, "onBindViewHolder: " + this)

                // Set click listener for edit button
                binding.editTask.setOnClickListener {
                    listener?.onEditItemClicked(this, position) // Call onEditItemClicked method of listener
                }

                // Set click listener for delete button
                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteItemClicked(this, position) // Call onDeleteItemClicked method of listener
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size // Return the number of items in the list
    }

    // Interface for handling item click events
    interface TaskAdapterInterface {
        fun onDeleteItemClicked(toDoData: ToDoData, position: Int) // Method to handle delete item event
        fun onEditItemClicked(toDoData: ToDoData, position: Int) // Method to handle edit item event
    }
}
