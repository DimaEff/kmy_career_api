package ru.my_career.auth

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.my_career.common.notifications.NotificationServiceImpl
import ru.my_career.common.notifications.dto.SmsDto

fun Application.configureAuthRouting() {
    routing {
        post("/login") {
            val body = call.receive<SmsDto>()

            val a = NotificationServiceImpl()
            a.sendSms(body).let { call.respond(it) }
        }

        post("/register") {

        }
    }
}