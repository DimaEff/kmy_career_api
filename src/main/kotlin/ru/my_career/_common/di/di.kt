package ru.my_career._common.di

import org.koin.dsl.module
import ru.my_career.roles.repositories.PermissionsRepository
import ru.my_career.roles.repositories.RolesRepository
import ru.my_career.roles.services.PermissionsService
import ru.my_career.roles.services.PermissionsServiceImpl
import ru.my_career.roles.services.RolesService
import ru.my_career.roles.services.RolesServiceImpl

val applicationModule = module {
    // repository
    single { PermissionsRepository() }
    single { RolesRepository() }

    // services
    single<PermissionsService> { PermissionsServiceImpl(get<PermissionsRepository>()) }
    single<RolesService> { RolesServiceImpl(get<RolesRepository>(), get<PermissionsRepository>()) }
}