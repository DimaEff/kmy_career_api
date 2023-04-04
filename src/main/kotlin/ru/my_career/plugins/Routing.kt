package ru.my_career.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import ru.my_career.config.Database

fun Application.configureRouting() {

    routing {
        get("/ping") {
            call.respondText("Ping!")
        }
    }
}
