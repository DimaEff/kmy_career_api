package ru.my_career.roles.repositories

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.my_career._common.database.Id
import ru.my_career.roles.CommonRoleTitle
import ru.my_career.roles.dto.CreateRoleDto
import ru.my_career.roles.dto.RoleDto
import ru.my_career.roles.dto.UpdateRolePermissionsDto
import ru.my_career.roles.tables.RolesPermissionsTable
import ru.my_career.roles.tables.RolesTable

class RolesRepository {
    private val logger = LoggerFactory.getLogger(RolesRepository::class.java)

    fun getAllRoles(): Collection<RoleDao>? = try {
        var roles: Collection<RoleDao>? = null

        transaction {
            roles = RolesTable.selectAll().map { RoleDao.wrapRow(it) }
        }

        roles
    } catch (e: Throwable) {
        logger.error("getAllRoles: ${e.message}")
        null
    }

    fun getRoleById(id: Id): RoleDao? = try {
        transaction {
            RoleDao.findById(id)
        }
    } catch (e: Throwable) {
        logger.error("getRoleById: ${e.message}")
        null
    }

    fun createRole(dto: CreateRoleDto, rolesPermissions: Collection<PermissionDao>): RoleDao? {
        return try {
            val commonTitle = if (dto.commonRoleTitle == null) null else CommonRoleTitle.getValueBy(dto.commonRoleTitle)
            val createdRole = transaction {
                RoleDao.new {
                    title = dto.title
                    description = dto.description
                    commonRoleTitle = commonTitle
                    permissions = SizedCollection(rolesPermissions)
                }
            }

            createdRole
        } catch (e: Throwable) {
            logger.error("Error while creating a new role: ${e.message}")
            null
        }
    }

    fun addPermissionToRole(dto: UpdateRolePermissionsDto): Unit? {
        return try {
            transaction {
                RolesPermissionsTable.batchInsert(dto.permissions) { p ->
                    this[RolesPermissionsTable.role] = dto.roleId
                    this[RolesPermissionsTable.permission] = p
                }
            }
            Unit
        } catch (e: Throwable) {
            logger.error("Failed addPermissionToRole: ${e.message}")
            null
        }
    }

    fun removePermissionsFromRole(dto: UpdateRolePermissionsDto): Unit? = try {
        transaction {
            RolesPermissionsTable.deleteWhere {
                (role eq dto.roleId) and (permission inList dto.permissions)
            }
        }
        Unit
    } catch (e: Throwable) {
        logger.error("Failed removePermissionsFromRole: ${e.message}")
        null
    }
}

class RoleDao(id: EntityID<Id>) : IntEntity(id) {
    companion object : IntEntityClass<RoleDao>(RolesTable)

    var title by RolesTable.title
    var description by RolesTable.description
    var commonRoleTitle by RolesTable.commonRoleTitle
    var permissions by PermissionDao via RolesPermissionsTable
}

fun RoleDao.toDto(): RoleDto = RoleDto(
    id = this.id.value,
    title = this.title,
    description = this.description,
    commonRoleTitle = this.commonRoleTitle
)
