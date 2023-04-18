package ru.my_career.roles

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career._common.requests.getJwtInfo
import ru.my_career.roles.dto.CreateRoleDto
import ru.my_career.roles.dto.UpdateRolePermissionsDto
import ru.my_career.roles.services.RolesService

fun Application.configRolesRouting() {
    routing {
        val rolesService by inject<RolesService>()

        route("/roles") {
            get {
                val res = rolesService.getUserRolesForCompany(getJwtInfo(call))
                call.respond(res.statusCode, res)
            }

            post {
                val body = call.receive<CreateRoleDto>()
                val res = rolesService.createRole(body, getJwtInfo(call))
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