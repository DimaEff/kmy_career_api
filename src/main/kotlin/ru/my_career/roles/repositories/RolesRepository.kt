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
import ru.my_career.companies.repositories.CompanyDao
import ru.my_career.companies.repositories.toDto
import ru.my_career.companies.tables.CompaniesUsersRolesTable
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
        val rolesDao = mutableListOf<RoleDao>()
        transaction {
            RoleDao.all().toCollection(rolesDao)
        }
    } catch (e: Error) {
        logger.error(e.message)
        null
    }

    fun deleteRoles(ids: Collection<Id>): Unit? = try {
        transaction {
            getRolesByIds(ids).forEach { it.delete() }
        }
    } catch (e: Error) {
        logger.error(e.message)
        null
    }

    fun getCompanyRoles(companyId: Id): Collection<RoleDao>? {
        var rolesIds: Collection<Id>? = null
        transaction {
            rolesIds = CompaniesUsersRolesTable.select { CompaniesUsersRolesTable.company eq companyId }
                .map { it[CompaniesUsersRolesTable.role].value }
        }

        if (rolesIds == null) {
            logger.warn("Roles for company $companyId not found")
            return null
        }

        return getRolesByIds(rolesIds as Collection<Id>)
    }

    fun getUserRolesForCompany(companyId: Id, userId: Id): Collection<RoleDao> {
        var rolesIds: Collection<Id> = emptySet()
        transaction {
            rolesIds = CompaniesUsersRolesTable.select {
                (CompaniesUsersRolesTable.company eq companyId) and
                        (CompaniesUsersRolesTable.user eq userId)
            }.map { it[CompaniesUsersRolesTable.role].value }
        }

        return getRolesByIds(rolesIds)
    }

    fun getRolesByIds(ids: Collection<Id>): Collection<RoleDao> {
        var roles: Collection<RoleDao> = emptySet()
        transaction {
            roles = RolesTable.select { RolesTable.id inList ids }.map { RoleDao.wrapRow(it) }
        }

        return roles
    }

    fun getRoleById(id: Id): RoleDao? = try {
        transaction {
            RoleDao.findById(id)
        }
    } catch (e: Throwable) {
        logger.error("getRoleById: ${e.message}")
        null
    }

    fun createRole(
        dto: CreateRoleDto,
        rolesPermissions: Collection<PermissionDao>,
        companyId: Id,
        userId: Id?
    ): RoleDao? {
        return try {
            val commonTitle = if (dto.commonRoleTitle == null) null else CommonRoleTitle.getValueBy(dto.commonRoleTitle)

            val companyDao = transaction {
                CompanyDao.findById(companyId)
            } ?: return null

            val createdRole = transaction {
                RoleDao.new {
                    title = dto.title
                    description = dto.description
                    commonRoleTitle = commonTitle
                    permissions = SizedCollection(rolesPermissions)
                    company = companyDao
                }
            }
            addRoleForCompanyAndUser(companyId, userId, createdRole.id.value)

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

    fun addRoleForCompanyAndUser(companyId: Id, userId: Id?, roleId: Id): Unit {
        transaction {
            CompaniesUsersRolesTable.insert {
                it[CompaniesUsersRolesTable.company] = companyId
                it[CompaniesUsersRolesTable.user] = userId
                it[CompaniesUsersRolesTable.role] = roleId
            }
        }
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
    var company by CompanyDao referencedOn RolesTable.company
}

fun RoleDao.toDto(): RoleDto = transaction {
    RoleDto(
        id = this@toDto.id.value,
        title = this@toDto.title,
        description = this@toDto.description,
        permissions = this@toDto.permissions.map { it.toDto() },
        company = this@toDto.company.toDto(),
        commonRoleTitle = this@toDto.commonRoleTitle
    )
}
