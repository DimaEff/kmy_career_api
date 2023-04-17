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
import ru.my_career.roles.dto.CreateUpdateCommonRolePermissionsDto
import ru.my_career.roles.dto.RoleDto
import ru.my_career.roles.dto.UpdateRolePermissionsDto
import ru.my_career.roles.tables.CommonRoleTitlePermissionTable
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

    fun addCommonRolePermissions(dto: CreateUpdateCommonRolePermissionsDto): Unit? = try {
        if (checkIsCommonRoleExistsPermissions(dto)) {
            null
        } else {
            val commonRoleTitle = CommonRoleTitle.valueOf(dto.commonRoleTitle)
            transaction {
                CommonRoleTitlePermissionTable.batchInsert(dto.permissions) {
                    this[CommonRoleTitlePermissionTable.permission] = it
                    this[CommonRoleTitlePermissionTable.commonRoleTitle] = commonRoleTitle
                }
            }

            val commonRolesIds = getRolesWithCommonTitle(commonRoleTitle)
            commonRolesIds.forEach { addPermissionToRole(UpdateRolePermissionsDto(it, dto.permissions)) }
            Unit
        }
    } catch (e: Throwable) {
        logger.error("Failed createCommonRolePermissions: ${e}")
        null
    }

    fun removePermissionsFromCommonRole(dto: CreateUpdateCommonRolePermissionsDto): Unit? = try {
        val commonRoleTitle = CommonRoleTitle.valueOf(dto.commonRoleTitle)
        transaction {
            CommonRoleTitlePermissionTable.deleteWhere {
                (CommonRoleTitlePermissionTable.commonRoleTitle eq commonRoleTitle) and
                        (CommonRoleTitlePermissionTable.permission inList dto.permissions)
            }
        }

        val commonRolesIds = getRolesWithCommonTitle(commonRoleTitle)
        commonRolesIds.forEach { removePermissionsFromRole(UpdateRolePermissionsDto(it, dto.permissions)) }
        Unit
    } catch (e: Throwable) {
        logger.error("Failed removePermissionsFromCommonRole: ${e.message}")
        null
    }

    fun getPermissionsForCommonRole(commonRoleTitle: CommonRoleTitle): Collection<Id> {
        var permissionsIds: Collection<Id> = emptySet()
        transaction {
            permissionsIds =
                CommonRoleTitlePermissionTable.select { CommonRoleTitlePermissionTable.commonRoleTitle eq commonRoleTitle }
                    .map { it[CommonRoleTitlePermissionTable.permission].value }
        }

        return permissionsIds
    }

    private fun checkIsCommonRoleExistsPermissions(dto: CreateUpdateCommonRolePermissionsDto): Boolean {
        val permissionsIds = getPermissionsForCommonRole(CommonRoleTitle.valueOf(dto.commonRoleTitle))
        return permissionsIds.any { it in dto.permissions }
    }

    private fun getRolesWithCommonTitle(commonRoleTitle: CommonRoleTitle): Collection<Id> {
        var commonRolesIds: Collection<Id> = emptySet()
        transaction {
            commonRolesIds = RolesTable.select { RolesTable.commonRoleTitle eq commonRoleTitle }
                .map { RoleDao.wrapRow(it).id.value }
        }

        return commonRolesIds
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
