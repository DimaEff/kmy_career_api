package ru.my_career

import io.ktor.server.application.*
import ru.my_career.auth.configureAuthRouting
import ru.my_career.plugins.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

// module used in application.conf:ktor.application.modules
fun Application.module() {
//    routing
    configureRouting()
    configureAuthRouting()

    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureSockets()
    configureKoin()
    initdb()
}
