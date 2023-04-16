package ru.my_career._common.di

import org.koin.dsl.module
import ru.my_career.roles.repositories.PermissionsRepository
import ru.my_career.roles.services.PermissionsService
import ru.my_career.roles.services.PermissionsServiceImpl

val applicationModule = module {
    // repository
    single { PermissionsRepository() }

    // services
    single<PermissionsService> { PermissionsServiceImpl(get<PermissionsRepository>()) }
}