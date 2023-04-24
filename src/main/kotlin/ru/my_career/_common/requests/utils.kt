package ru.my_career._common.requests

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import ru.my_career.auth.dto.JwtInfo

fun getJwtInfo(call: ApplicationCall): JwtInfo {
    // principal validated in the security plugin
    val principal = call.principal<JWTPrincipal>()!!

    val userId = principal.payload.getClaim("userId").asInt()
    val companyId = principal.payload.getClaim("companyId").asInt()
    return JwtInfo(userId, companyId)
}
