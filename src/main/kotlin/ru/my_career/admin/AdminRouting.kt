package ru.my_career.admin

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career.roles.dto.CreatePermissionDto
import ru.my_career.roles.dto.CreateUpdateCommonRolePermissionsDto
import ru.my_career.roles.services.PermissionsService
import ru.my_career.roles.services.RolesService

fun Application.configAdminRouting() {
    routing {
        val permissionsService by inject<PermissionsService>()
        val rolesService by inject<RolesService>()

        route("/admin") {
            route("/roles") {
                get {

                }

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

                route("/common_role_permissions") {
                    post("/add") {
                        val body = call.receive<CreateUpdateCommonRolePermissionsDto>()
                        val res = rolesService.addCommonRolePermissions(body)
                        call.respond(res.statusCode, res)
                    }

                    post("/remove") {
                        val body = call.receive<CreateUpdateCommonRolePermissionsDto>()
                        val res = rolesService.removePermissionsFromCommonRole(body)
                        call.respond(res.statusCode, res)
                    }
                }
            }
        }

        route("/dev/init") {
            get {
                val permissionsDtos = setOf<CreatePermissionDto>(
                    CreatePermissionDto(title = "title1", description = "description1", permissionType = "READ"),
                    CreatePermissionDto(title = "title1", description = "description2", permissionType = "WRITE"),
                    CreatePermissionDto(title = "title2", description = "description1", permissionType = "READ"),
                    CreatePermissionDto(title = "title2", description = "description2", permissionType = "WRITE"),
                    CreatePermissionDto(title = "title3", description = "description3", permissionType = "READ"),
                    CreatePermissionDto(title = "title3", description = "description3", permissionType = "WRITE"),
                )
                permissionsDtos.forEach(permissionsService::createPermission)

                call.respond(HttpStatusCode.OK)
            }
        }
    }
}