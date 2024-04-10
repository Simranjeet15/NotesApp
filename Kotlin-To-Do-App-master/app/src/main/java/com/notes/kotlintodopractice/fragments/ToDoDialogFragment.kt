package com.notes.kotlintodopractice.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.notes.kotlintodopractice.databinding.FragmentToDoDialogBinding
import com.notes.kotlintodopractice.utils.model.ToDoData
import com.google.android.material.textfield.TextInputEditText

class ToDoDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentToDoDialogBinding // View binding for this dialog fragment
    private var listener: OnDialogNextBtnClickListener? = null // Listener for button click events
    private var toDoData: ToDoData? = null // Data model for ToDo item

    // Method to set the listener for button click events
    fun setListener(listener: OnDialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "DialogFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) =
            ToDoDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("taskId", taskId)
                    putString("task", task)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentToDoDialogBinding.inflate(inflater, container, false) // Inflate layout using view binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data passed via arguments and populate views
        if (arguments != null) {
            toDoData = ToDoData(arguments?.getString("taskId").toString(), arguments?.getString("task").toString())
            binding.todoEt.setText(toDoData?.task)
        }

        // Close button click listener
        binding.todoClose.setOnClickListener {
            dismiss() // Dismiss the dialog fragment
        }

        // Next button click listener
        binding.todoNextBtn.setOnClickListener {
            val todoTask = binding.todoEt.text.toString() // Get text from EditText
            if (todoTask.isNotEmpty()) { // Check if text is not empty
                if (toDoData == null) {
                    listener?.saveTask(todoTask, binding.todoEt) // Call saveTask method of listener if data is new
                } else {
                    toDoData!!.task = todoTask // Update task text in data model
                    listener?.updateTask(toDoData!!, binding.todoEt) // Call updateTask method of listener if data exists
                }
            }
        }
    }

    // Interface for handling button click events
    interface OnDialogNextBtnClickListener {
        fun saveTask(todoTask: String, todoEdit: TextInputEditText) // Method to handle save task event
        fun updateTask(toDoData: ToDoData, todoEdit: TextInputEditText) // Method to handle update task event
    }
}
