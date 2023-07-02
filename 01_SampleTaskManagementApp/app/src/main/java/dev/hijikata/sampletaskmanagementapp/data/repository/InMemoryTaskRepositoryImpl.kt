package dev.hijikata.sampletaskmanagementapp.data.repository

import dev.hijikata.sampletaskmanagementapp.model.task.Task
import java.lang.IllegalStateException
import java.time.ZonedDateTime
import java.time.temporal.TemporalUnit
import java.util.UUID

object InMemoryTaskRepositoryImpl : TaskRepository {
    private val dummyInProgressTaskList = listOf(
        Task.InProgressTask(
            UUID.randomUUID().variant(),
            "タスクがきたにょ。",
            "来てくれてありがとう\n来てくれてありがとう\n帰れ",
            null
        ),
        Task.InProgressTask(
            UUID.randomUUID().variant(),
            "タスクがきたにょ。",
            "来てくれてありがとう\n来てくれてありがとう\n帰れ",
            ZonedDateTime.now().withHour(23).withMinute(59)
        ),
        Task.InProgressTask(
            UUID.randomUUID().variant(),
            "タスクがきたにょ。",
            "来てくれてありがとう\n来てくれてありがとう\n帰れ",
            ZonedDateTime.now().plusDays(1)
        ),
        Task.InProgressTask(
            UUID.randomUUID().variant(),
            "タスクがきたにょ。",
            "来てくれてありがとう\n来てくれてありがとう\n帰れ",
            ZonedDateTime.now().plusDays(2)
        ),
        Task.InProgressTask(
            UUID.randomUUID().variant(),
            "タスクがきたにょ。",
            "来てくれてありがとう\n来てくれてありがとう\n帰れ",
            ZonedDateTime.now().plusDays(10)
        ),
    )
    private val completedTaskList: MutableList<Task.CompletedTask> = mutableListOf()
    private val inProgressTaskList: MutableList<Task.InProgressTask> = dummyInProgressTaskList.toMutableList()

    override suspend fun createTask(draftTask: Task.DraftTask): Task.InProgressTask {
        val id = UUID.randomUUID().variant()
        inProgressTaskList.add(
            Task.InProgressTask(
                id,
                draftTask
            )
        )
        return inProgressTaskList.find { it.id == id }
            ?: throw IllegalStateException("タスクの作成に失敗しました。")
    }

    override suspend fun getAllTaskList(): List<Task> {
        return completedTaskList + inProgressTaskList
    }

    override suspend fun getInProgressTaskList(): List<Task.InProgressTask> {
        return inProgressTaskList
    }

    override suspend fun getCompletedTaskList(): List<Task.CompletedTask> {
        return completedTaskList
    }

    override suspend fun editInProgressTask(
        targetTask: Task.InProgressTask,
        draftTask: Task.DraftTask
    ): Task.InProgressTask {
        val targetIndex = inProgressTaskList.indexOfFirst { it.id == targetTask.id }
        inProgressTaskList[targetIndex] = Task.InProgressTask(
            inProgressTaskList[targetIndex].id,
            draftTask
        )
        return inProgressTaskList[targetIndex]
    }

    override suspend fun completeInProgressTask(task: Task.InProgressTask): Task.CompletedTask {
        inProgressTaskList.removeIf { it.id == task.id }
        completedTaskList.add(Task.CompletedTask(task))
        return completedTaskList.find { it.id == task.id }
            ?: throw IllegalStateException("タスクの作成に失敗しました。")
    }

    override suspend fun revertCompletedTask(task: Task.CompletedTask): Task.InProgressTask {
        completedTaskList.removeIf { it.id == task.id }
        inProgressTaskList.add(Task.InProgressTask(task))
        return inProgressTaskList.find { it.id == task.id }
            ?: throw IllegalStateException("タスクの作成に失敗しました。")
    }

    override suspend fun deleteInProgressTask(task: Task.InProgressTask) {
        inProgressTaskList.removeIf { it.id == task.id }
    }

    override suspend fun deleteCompletedTask(task: Task.CompletedTask) {
        completedTaskList.removeIf { it.id == task.id }
    }
}