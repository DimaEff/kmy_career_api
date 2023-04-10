package ru.my_career.users

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career.users.models.CreateUserDto
import ru.my_career.users.services.UsersService

fun Application.configureUsersRouting() {
    routing() {
        val usersService by inject<UsersService>()

        route("/users") {
            post {
                val body = call.receive<CreateUserDto>()

                usersService.createUser(body)?.let { call.respond(HttpStatusCode.Created, it) } ?:
                call.respond(HttpStatusCode.BadRequest, "bad")
            }
        }
    }
}
