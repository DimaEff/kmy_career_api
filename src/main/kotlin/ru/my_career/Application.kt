package ru.my_career

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.my_career._common.types.ResponseEntity
import ru.my_career._plugins.*
import ru.my_career.admin.configAdminRouting
import ru.my_career.auth.configureAuthRouting
import ru.my_career.companies.configCompaniesRouting
import ru.my_career.roles.configRolesRouting
import ru.my_career.tasks.configTasksRouting

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

// module used in application.conf:ktor.application.modules
fun Application.module() {
//    plugins
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureSockets()
    configureKoin()
    initdb()

//    routing
    configureAuthRouting()
    configRolesRouting()
    configAdminRouting()
    configCompaniesRouting()
    configureAuthRouting()
    configTasksRouting()
}
