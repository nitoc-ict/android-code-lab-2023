package dev.hijikata.sampletaskmanagementapp.data.repository

import dev.hijikata.sampletaskmanagementapp.model.task.Task

interface TaskRepository {
    suspend fun createTask(draftTask: Task.DraftTask): Task.InProgressTask

    suspend fun getAllTaskList(): List<Task>
    suspend fun getInProgressTaskList(): List<Task.InProgressTask>
    suspend fun getCompletedTaskList(): List<Task.CompletedTask>

    suspend fun editInProgressTask(targetTask: Task.InProgressTask, draftTask: Task.DraftTask): Task.InProgressTask
    suspend fun completeInProgressTask(task: Task.InProgressTask): Task.CompletedTask
    suspend fun revertCompletedTask(task: Task.CompletedTask): Task.InProgressTask

    suspend fun deleteInProgressTask(task: Task.InProgressTask): Unit
    suspend fun deleteCompletedTask(task: Task.CompletedTask): Unit

}