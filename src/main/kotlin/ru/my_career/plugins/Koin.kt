package ru.my_career.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.my_career.common.di.applicationModule

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(applicationModule)
    }
}