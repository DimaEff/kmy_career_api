package ru.my_career.auth

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import ru.my_career._common.notifications.dto.SmsDto

fun Application.configureAuthRouting() {
    routing {
        route("/login") {
            post {
                val body = call.receive<SmsDto>()

            }

            post("/login/confirmation") {

            }
        }

        post("/register") {

        }
    }
}