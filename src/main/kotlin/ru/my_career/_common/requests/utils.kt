package ru.my_career._common.requests

import io.ktor.server.application.*
import ru.my_career.auth.dto.JwtInfo

fun getJwtInfo(call: ApplicationCall): JwtInfo {
    val queries = call.request.queryParameters
    return JwtInfo(queries["userId"]?.toInt()!!, queries["companyId"]?.toInt()!!)
}
