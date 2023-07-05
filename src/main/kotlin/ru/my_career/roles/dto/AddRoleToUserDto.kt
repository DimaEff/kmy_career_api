package ru.my_career.roles.dto

import ru.my_career._common.database.Id

data class AddRoleToUserDto(
    val userId: Id,
    val roleId: Id,
)
