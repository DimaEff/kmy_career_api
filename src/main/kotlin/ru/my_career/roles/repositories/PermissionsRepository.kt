package ru.my_career.roles.repositories

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.my_career._common.database.Id
import ru.my_career.roles.dto.PermissionDto
import ru.my_career.roles.tables.PermissionsTable

class PermissionsRepository {
    private val logger = LoggerFactory.getLogger(PermissionsRepository::class.java)

    fun createPermission(permission: PermissionDto): PermissionDao? {
        return try {
            transaction {
                PermissionDao.new {
                    title = permission.title
                    description = permission.description
                }
            }
        } catch (e: Throwable) {
            logger.error("Filed to create a permission: ${e.message}")
            null
        }
    }

    fun findByTitle(title: String): PermissionDao? {
        var permission: PermissionDao? = null

        transaction {
            permission = PermissionsTable.select { PermissionsTable.title eq title }.limit(1).firstOrNull()
                ?.let { PermissionDao.wrapRow(it) }
        }

        return permission
    }
}

class PermissionDao(id: EntityID<Id>) : IntEntity(id) {
    companion object : IntEntityClass<PermissionDao>(PermissionsTable)

    var title by PermissionsTable.title
    var description by PermissionsTable.description
}

fun PermissionDao.toDto(): PermissionDto =
    PermissionDto(id = this.id.value, title = this.title, description = this.description)

