package dev.hijikata.sampletaskmanagementapp.model.task

import java.time.ZonedDateTime

sealed class Task {
    abstract val title: String
    abstract val description: String
    abstract val deadline: ZonedDateTime

    data class DraftTask(
        override val title: String,
        override val description: String,
        override val deadline: ZonedDateTime,
    ) : Task()

    data class InProgressTask(
        val id: Int,
        override val title: String,
        override val description: String,
        override val deadline: ZonedDateTime,
    ) : Task() {
        constructor(id: Int, draftTask: DraftTask) : this(
            id,
            draftTask.title,
            draftTask.description,
            draftTask.deadline
        )
    }

    data class CompletedTask(
        val id: Int,
        override val title: String,
        override val description: String,
        override val deadline: ZonedDateTime,
    ) : Task() {
        constructor(inProgressTask: InProgressTask) :this(
            inProgressTask.id,
            inProgressTask.title,
            inProgressTask.description,
            inProgressTask.deadline,
        )
    }
}
