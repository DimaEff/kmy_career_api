package ru.my_career.roles

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career.roles.models.PermissionDto
import ru.my_career.roles.models.toModel
import ru.my_career.roles.services.PermissionService

fun Application.configRolesRouting() {
    routing {
        val permissionService by inject<PermissionService>()

        route("/roles") {
            get {
                call.respondText("roles")
            }

            post {
                val body = call.receive<PermissionDto>()
                val permission = body.toModel()

                permissionService.createPermission(permission)?.let { permissionId ->
                    call.respond(HttpStatusCode.Created, permissionId)
                } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST)
            }
        }
    }
}