package ru.my_career.tasks

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.my_career._common.constants.JWT_AUTH_METHOD
import ru.my_career._common.requests.getJwtInfo
import ru.my_career.tasks.dto.CreateTaskDto
import ru.my_career.tasks.services.TasksService

fun Application.configTasksRouting() {
    val tasksService by inject<TasksService>()

    routing {
        authenticate(JWT_AUTH_METHOD) {
            route("/tasks") {
                post {
                    val body = call.receive<CreateTaskDto>()
                    val jwtInfo = getJwtInfo(call)
                    val res = tasksService.createTask(body, jwtInfo.companyId, jwtInfo.userId)
                    call.respond(res.statusCode, res)
                }
            }
        }
    }
}
