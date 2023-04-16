package ru.my_career._plugins

import io.ktor.server.application.*
import ru.my_career._common.database.DatabaseFactory

fun Application.initdb() {
    DatabaseFactory.init()
}