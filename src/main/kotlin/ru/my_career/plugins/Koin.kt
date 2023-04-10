package ru.my_career.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ru.my_career.companies.companiesModule
import ru.my_career.roles.rolesModule
import ru.my_career.users.usersModule

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(rolesModule, usersModule, companiesModule)
    }
}