package ru.my_career.roles

import ru.my_career.common.database.PgType

enum class CommonRoleTitle {
    A, B;

    companion object : PgType<CommonRoleTitle>() {
        override val className = CommonRoleTitle::class.simpleName
        override val values = CommonRoleTitle.values().map { it.toString() }
        override fun getValueBy(value: Any) = valueOf(value as String)
    }
}
