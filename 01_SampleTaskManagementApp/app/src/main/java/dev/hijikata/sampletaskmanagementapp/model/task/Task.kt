package dev.hijikata.sampletaskmanagementapp.model.task

import java.time.ZonedDateTime

sealed class Task {
    abstract val id: Int
    abstract val title: String
    abstract val description: String
    abstract val deadline: ZonedDateTime?

    data class DraftTask(
        override val title: String,
        override val description: String,
        override val deadline: ZonedDateTime?,
    ) : Task() {
        override val id: Int = 0
    }

    data class InProgressTask(
        override val id: Int,
        override val title: String,
        override val description: String,
        override val deadline: ZonedDateTime?,
    ) : Task() {
        constructor(id: Int, draftTask: DraftTask) : this(
            id,
            draftTask.title,
            draftTask.description,
            draftTask.deadline
        )
        constructor(completedTask: CompletedTask) : this(
            completedTask.id,
            completedTask.title,
            completedTask.description,
            completedTask.deadline,
        )
    }

    data class CompletedTask(
        override val id: Int,
        override val title: String,
        override val description: String,
        override val deadline: ZonedDateTime?,
    ) : Task() {
        constructor(inProgressTask: InProgressTask) :this(
            inProgressTask.id,
            inProgressTask.title,
            inProgressTask.description,
            inProgressTask.deadline,
        )
    }
}
