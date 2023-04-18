package ru.my_career.users

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career.users.dto.CreateUserDto
import ru.my_career.users.services.UsersService

fun Application.configUsersRouting() {
    routing {
        val usersService by inject<UsersService>()

        route("/users") {
            get("/{id}") {
                val id = call.parameters["id"]
                // TODO: implement the `id` parameter validation
                val res = usersService.getUserById(id?.toInt()!!)
                call.respond(res.statusCode, res)
            }

            post {
                val body = call.receive<CreateUserDto>()
                val res = usersService.createUser(body)
                call.respond(res.statusCode, res)
            }
        }
    }
}
