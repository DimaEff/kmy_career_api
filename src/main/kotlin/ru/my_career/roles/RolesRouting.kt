package ru.my_career.roles

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career.roles.dto.CreatePermissionDto
import ru.my_career.roles.services.PermissionsService

fun Application.configRolesRouting() {
    routing {
        val permissionsService by inject<PermissionsService>()

        route("/permissions") {
            post {
                val body = call.receive<CreatePermissionDto>()

                val res = permissionsService.createPermission(body)

                call.respond(res.statusCode, res)
            }
        }
    }
}