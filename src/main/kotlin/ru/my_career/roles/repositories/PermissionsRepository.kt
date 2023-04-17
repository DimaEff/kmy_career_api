package ru.my_career.roles.repositories

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.my_career._common.database.Id
import ru.my_career.roles.dto.PermissionDto
import ru.my_career.roles.tables.PermissionsTable
import ru.my_career.roles.tables.RolesPermissionsTable

class PermissionsRepository {
    private val logger = LoggerFactory.getLogger(PermissionsRepository::class.java)

    fun getAllPermissions(): Collection<PermissionDao>? = try {
        var permissions: Collection<PermissionDao>? = null

        transaction {
            permissions = PermissionsTable.selectAll().map { PermissionDao.wrapRow(it) }
        }
        permissions
    } catch (e: Throwable) {
        logger.error("Failed to getting all permissions: ${e.message}")
        null
    }

    fun getPermissionsByIds(ids: Collection<Id>): Collection<PermissionDao>? = try {
        var permissions: Collection<PermissionDao>? = null

        transaction {
            permissions = PermissionsTable.select { PermissionsTable.id inList ids }.map { PermissionDao.wrapRow(it) }
        }

        permissions
    } catch (e: Throwable) {
        logger.error("Failed getPermissionsById: ${e.message}")
        null
    }

    fun checkIsAllPermissionsExistsAndGetPermissions(ids: Collection<Id>): Collection<PermissionDao>? {
        val permissions = getPermissionsByIds(ids)

        return if (permissions == null || permissions.any { it.id.value !in ids }) {
            null
        } else {
            permissions
        }
    }

    fun createPermission(permission: PermissionDto): PermissionDao? {
        return try {
            transaction {
                PermissionDao.new {
                    title = permission.title
                    description = permission.description
                }
            }
        } catch (e: Throwable) {
            logger.error("Failed to create a permission: ${e.message}")
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

    fun getAllPermissionsOfRole(roleId: Id): Collection<PermissionDao>? {
        var permissionsIds: Collection<Id>? = null
        transaction {
            permissionsIds = RolesPermissionsTable.select { RolesPermissionsTable.role eq roleId }
                .map { it[RolesPermissionsTable.permission].value }
        }

        if (permissionsIds == null) {
            return null
        }

        return getPermissionsByIds(permissionsIds as Collection<Id>)
    }
}

class PermissionDao(id: EntityID<Id>) : IntEntity(id) {
    companion object : IntEntityClass<PermissionDao>(PermissionsTable)

    var title by PermissionsTable.title
    var description by PermissionsTable.description
}

fun PermissionDao.toDto(): PermissionDto =
    PermissionDto(id = this.id.value, title = this.title, description = this.description)

