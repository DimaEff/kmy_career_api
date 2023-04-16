package ru.my_career.roles.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.my_career.common.database.Id
import ru.my_career.roles.tables.RolesPermissionsTable
import ru.my_career.roles.tables.RolesTable

class RoleEntity(id: EntityID<Id>): IntEntity(id) {
    companion object: IntEntityClass<RoleEntity>(RolesTable)
    var title by RolesTable.title
    var description by RolesTable.description
    var commonRoleId by RolesTable.commonRoleTitle
    var permissions by PermissionEntity via RolesPermissionsTable
}