package ru.my_career._common.requests

import io.ktor.server.application.*
import ru.my_career._common.database.Id

fun getUserCompanyFromQueries(call: ApplicationCall): Pair<Id, Id> {
    val queries = call.request.queryParameters
    return Pair(queries["userId"]?.toInt()!!, queries["companyId"]?.toInt()!!)
}
