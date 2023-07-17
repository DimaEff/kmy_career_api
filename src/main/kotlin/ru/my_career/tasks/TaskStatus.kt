package ru.my_career.tasks

import ru.my_career._common.database.PgType

enum class TaskStatus {
    TODO,
    INPROGRESS,
    REVIEW,
    COMPLETED;

    companion object: PgType<TaskStatus>() {
        override val className: String? = TaskStatus::class.simpleName
        override val values: Collection<String> = TaskStatus.values().map { it.toString() }
        override fun getValueBy(value: Any): TaskStatus = valueOf(value as String)
    }
}