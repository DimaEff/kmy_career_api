package ru.my_career.roles

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career.roles.dto.CreatePermissionDto
import ru.my_career.roles.dto.CreateRoleDto
import ru.my_career.roles.dto.UpdateRolePermissionsDto
import ru.my_career.roles.services.PermissionsService
import ru.my_career.roles.services.RolesService

fun Application.configRolesRouting() {
    routing {
        val permissionsService by inject<PermissionsService>()
        val rolesService by inject<RolesService>()

        route("/permissions") {
            get {
                val res = permissionsService.getAllPermissions()
                call.respond(res.statusCode, res)
            }

            post {
                val body = call.receive<CreatePermissionDto>()

                val res = permissionsService.createPermission(body)

                call.respond(res.statusCode, res)
            }
        }

        route("/roles") {
            get {
                val res = rolesService.getAllRoles()
                call.respond(res.statusCode, res)
            }

            post {
                val body = call.receive<CreateRoleDto>()
                val res = rolesService.createRole(body)
                call.respond(res.statusCode, res)
            }

            get("/{id}/permissions") {
                val id = call.parameters["id"]
                // TODO: validate parameters["id"]
                val res = rolesService.getRolePermissions(id?.toInt() ?: 0)
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