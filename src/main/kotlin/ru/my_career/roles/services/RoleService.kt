package ru.my_career.roles.services

import ru.my_career.roles.models.Role

interface RoleService {
    suspend fun createRole(role: Role): String?
}