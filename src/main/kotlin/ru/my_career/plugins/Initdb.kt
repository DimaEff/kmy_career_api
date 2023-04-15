package ru.my_career.plugins

import io.ktor.server.application.*
import ru.my_career.config.DatabaseFactory

fun Application.initdb() {
    DatabaseFactory.init()
}