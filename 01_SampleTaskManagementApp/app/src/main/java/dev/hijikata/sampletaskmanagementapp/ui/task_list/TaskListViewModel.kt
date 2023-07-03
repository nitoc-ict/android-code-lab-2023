package dev.hijikata.sampletaskmanagementapp.ui.task_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.hijikata.sampletaskmanagementapp.data.repository.InMemoryTaskRepositoryImpl
import dev.hijikata.sampletaskmanagementapp.model.task.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskListViewModel(application: Application) : AndroidViewModel(application){
    private val taskRepository = InMemoryTaskRepositoryImpl

    private val _taskListStateFlow : MutableStateFlow<List<Task>> = MutableStateFlow(listOf())
    val taskListStateFlow : StateFlow<List<Task>> = _taskListStateFlow

    fun fetchTaskList() {
        viewModelScope.launch {
            val taskList = taskRepository.getAllTaskList()
            _taskListStateFlow.emit(taskList)
        }
    }

    fun completeInProgressTask(task: Task.InProgressTask) {
        viewModelScope.launch {
            taskRepository.completeInProgressTask(task)
            fetchTaskList()
        }
    }

    fun revertCompletedTask(task: Task.CompletedTask) {
        viewModelScope.launch {
            taskRepository.revertCompletedTask(task)
            fetchTaskList()
        }
    }
}