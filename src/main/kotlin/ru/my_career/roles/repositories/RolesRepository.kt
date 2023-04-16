package ru.my_career.roles.repositories

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.my_career._common.database.Id
import ru.my_career.roles.tables.RolesPermissionsTable
import ru.my_career.roles.tables.RolesTable

class RolesRepository {
}

class RoleDao(id: EntityID<Id>): IntEntity(id) {
    companion object: IntEntityClass<RoleDao>(RolesTable)
    var title by RolesTable.title
    var description by RolesTable.description
    var commonRoleId by RolesTable.commonRoleTitle
    var permissions by PermissionDao via RolesPermissionsTable
}
