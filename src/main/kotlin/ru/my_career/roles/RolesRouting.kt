package ru.my_career.roles

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career.roles.models.CreatePermissionDto
import ru.my_career.roles.models.CreateRoleDto
import ru.my_career.roles.services.PermissionService
import ru.my_career.roles.services.RoleService

fun Application.configRolesRouting() {
    routing {
        val permissionService by inject<PermissionService>()
        val roleService by inject<RoleService>()

        route("/permissions") {
            get("/{id}") {
                val id = call.parameters["id"]
                permissionService.getPermissionById(id)?.let {
                    call.respond(it)
                } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST)
            }

            post {
                val body = call.receive<CreatePermissionDto>()

                permissionService.createPermission(body)?.let {
                    call.respond(HttpStatusCode.Created, it)
                } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST)
            }

            delete("/{id}") {
                val id = call.parameters["id"]

                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST)
                } else {
                    permissionService.deletePermissionById(id)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }

        route("/roles") {
            get {
                call.respondText("roles")
            }

            post {
                val body = call.receive<CreateRoleDto>()

                roleService.createRole(body)?.let { roleId ->
                    call.respond(HttpStatusCode.Created, roleId)
                } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST)
            }
        }
    }
}