package ru.my_career.companies

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career._common.constants.JWT_AUTH_METHOD
import ru.my_career._common.requests.getJwtInfo
import ru.my_career.companies.dto.CreateCompanyDto
import ru.my_career.companies.services.CompaniesService

fun Application.configCompaniesRouting() {
    routing {
        val companiesService by inject<CompaniesService>()

        route("/companies") {
            get {
                val userId = call.request.queryParameters["userId"]
                // TODO: implement validating the `userId` field
                val res = companiesService.getUserCompanies(userId!!.toInt())
                call.respond(res.statusCode, res)
            }

            post {
                val body = call.receive<CreateCompanyDto>()
                val userId = call.request.queryParameters["userId"]
                // TODO: implement validating the `userId` field
                val res = companiesService.createCompany(body, userId!!.toInt())
                call.respond(res.statusCode, res)
            }

            authenticate(JWT_AUTH_METHOD) {
                get("/add_user") {
                    val phoneNumber = call.request.queryParameters["phoneNumber"]!!
                    val roleId = call.request.queryParameters["roleId"]!!.toInt()
                    // TODO: implement validating the `userId` and the roleId fields
                    val jwtInfo = getJwtInfo(call)
                    val res = companiesService.addUserToCompany(jwtInfo.companyId, phoneNumber, roleId)
                    call.respond(res.statusCode, res)
                }

                get("/users") {
                    val jwtInfo = getJwtInfo(call)
                    val res = companiesService.getCompanyUsers(jwtInfo.companyId)
                    call.respond(res.statusCode, res)
                }
            }
        }
    }
}
