package ru.my_career.companies

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career.companies.dto.CreateCompanyDto
import ru.my_career.companies.services.CompaniesService

fun Application.configCompaniesRouting() {
    routing {
        val companiesService by inject<CompaniesService>()

        route("/companies") {
            post {
                val body = call.receive<CreateCompanyDto>()
                val userId = call.request.queryParameters["userId"]
                // TODO: implement validating the `userId` field
                val res = companiesService.createCompany(body, userId?.toInt()!!)
                call.respond(res.statusCode, res)
            }

            get("/user_companies") {
                val userId = call.request.queryParameters["userId"]
                // TODO: implement validating the `userId` field
                val res = companiesService.getUserCompanies(userId?.toInt()!!)
                call.respond(res.statusCode, res)
            }

            get("/user_roles") {
                val userId = call.request.queryParameters["userId"]
                val companyId = call.request.queryParameters["companyId"]
                // TODO: implement validating the `userId` and `companyId` fields
                val res = companiesService.getUsersRolesForCompany(companyId?.toInt()!!, userId?.toInt()!!)
                call.respond(res.statusCode, res)
            }
        }
    }
}
