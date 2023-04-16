package ru.my_career.roles.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.my_career.common.database.Id
import ru.my_career.roles.tables.PermissionsTable

class PermissionEntity(id: EntityID<Id>): IntEntity(id) {
    companion object: IntEntityClass<PermissionEntity>(PermissionsTable)
    var title by PermissionsTable.title
    var description by PermissionsTable.description
}