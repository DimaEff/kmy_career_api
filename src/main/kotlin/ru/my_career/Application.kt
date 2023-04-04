package ru.my_career

import io.ktor.server.application.*
import ru.my_career.plugins.*
import ru.my_career.roles.configRolesRouting

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

// module used in application.conf:ktor.application.modules
fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureSockets()

    configureKoin()

//    routing
    configureRouting()
    configRolesRouting()
}
