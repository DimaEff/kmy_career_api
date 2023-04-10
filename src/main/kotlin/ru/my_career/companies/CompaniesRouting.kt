package ru.my_career.companies

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career.auth.models.JwtInfo
import ru.my_career.companies.models.CreateCompanyDto
import ru.my_career.companies.services.CompanyService

fun Application.configureCompaniesRouting() {
    routing {
        val companyService by inject<CompanyService>()

        route("/companies") {
            post {
                val body = call.receive<CreateCompanyDto>()

                companyService.createCompany(body, JwtInfo(userId = body.userId))
                    ?.let { call.respond(HttpStatusCode.Created, it) } ?:
                    call.respond(HttpStatusCode.BadRequest, "bad")
            }
        }
    }
}