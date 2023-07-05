package ru.my_career.roles

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career._common.constants.JWT_AUTH_METHOD
import ru.my_career._common.requests.getJwtInfo
import ru.my_career.roles.dto.AddRoleToUserDto
import ru.my_career.roles.dto.CreateRoleDto
import ru.my_career.roles.dto.UpdateRolePermissionsDto
import ru.my_career.roles.services.PermissionsService
import ru.my_career.roles.services.RolesService

fun Application.configRolesRouting() {
    routing {
        val rolesService by inject<RolesService>()
        val permissionsService by inject<PermissionsService>()

        authenticate(JWT_AUTH_METHOD) {
            route("/roles") {
                get {
                    val res = rolesService.getUserRolesForCompany(getJwtInfo(call))
                    call.respond(res.statusCode, res)
                }

                get("/company/{id}") {
                    // todo: validate the companyId field
                    val companyId = call.parameters["id"]!!
                    val res = rolesService.getCompanyRoles(companyId.toInt())
                    call.respond(res.statusCode, res)
                }

                post {
                    val body = call.receive<CreateRoleDto>()
                    val jwtInfo = getJwtInfo(call)
                    val res = rolesService.createRole(body, jwtInfo.companyId, jwtInfo.userId)
                    call.respond(res.statusCode, res)
                }

                get("/add_to_user") {
                    val userId = call.request.queryParameters["userId"]!!
                    val roleId = call.request.queryParameters["roleId"]!!
                    val jwtInfo = getJwtInfo(call)
                    val res =
                        rolesService.addRoleToUserForCompany(jwtInfo.companyId, userId.toInt(), roleId.toInt())
                    call.respond(res.statusCode, res)
                }

                get("/permissions") {
                    val res = permissionsService.getAllPermissions()
                    call.respond(res.statusCode, res)
                }

                get("/{id}/permissions") {
                    val id = call.parameters["id"]
                    // TODO: validate parameters["id"]
                    val res = rolesService.getRolePermissions(id?.toInt()!!)
                    call.respond(res.statusCode, res)
                }

                post("/permissions/add") {
                    val body = call.receive<UpdateRolePermissionsDto>()
                    val res = rolesService.addPermissionsToRole(body)
                    call.respond(res.statusCode, res)
                }

                post("/permissions/remove") {
                    val body = call.receive<UpdateRolePermissionsDto>()
                    val res = rolesService.removePermissionFromRole(body)
                    call.respond(res.statusCode, res)
                }
            }
        }
    }
}