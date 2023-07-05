package ru.my_career.auth

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career.auth.services.AuthService
import ru.my_career._common.confirmations.services.ConfirmationsService
import ru.my_career._common.constants.JWT_AUTH_METHOD
import ru.my_career._common.requests.getJwtInfo
import ru.my_career._common.users.dto.CreateUserDto
import ru.my_career._plugins.configureSecurity

fun Application.configureAuthRouting() {
    routing {
        val confirmationsService by inject<ConfirmationsService>()
        val authService by inject<AuthService>()

        get("/auth/send_phone_confirmation") {
            val phoneNumber = call.parameters["phoneNumber"]
            // TODO: validation the phoneNumber parameter
            val res = confirmationsService.addConfirmation(phoneNumber!!)
            call.respond(res.statusCode, res)
        }

        route("/login") {
            get() {
                val phoneNumber = call.parameters["phoneNumber"]
                val code = call.parameters["code"]
                // TODO: validation the phoneNumber parameter
                val res = authService.login(phoneNumber!!, code!!)
                call.respond(res.statusCode, res)
            }

            get("/company/{id}") {
                val userId = call.request.queryParameters["userId"]
                val companyId = call.parameters["id"]
                // todo: implement validation of the userId and the companyId fields
                val res = authService.authInCompany(userId!!.toInt(), companyId!!.toInt())
                call.respond(res.statusCode, res)
            }

            get("/auth_by_jwt") {
                val jwtInfo = getJwtInfo(call)
                val res = authService.authInCompany(jwtInfo.userId, jwtInfo.companyId)
                call.respond(res.statusCode, res)
            }
        }

        route("/register") {
            get("/check_phone_number") {
                val phoneNumber = call.parameters["phoneNumber"]
                val code = call.parameters["code"]
                // TODO: validation the phoneNumber and the code parameters
                val res = authService.checkCode(phoneNumber!!, code!!)
                call.respond(res.statusCode, res)
            }

            post() {
                val body = call.receive<CreateUserDto>()
                val res = authService.register(body)
                call.respond(res.statusCode, res)
            }
        }
    }
}