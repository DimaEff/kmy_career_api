package ru.my_career.roles

import org.koin.dsl.module
import ru.my_career.config.Database
import ru.my_career.config.MongoDB
import ru.my_career.roles.services.PermissionService
import ru.my_career.roles.services.PermissionServiceImpl
import ru.my_career.roles.services.RoleService
import ru.my_career.roles.services.RoleServiceImpl

val rolesModule = module {
    single<MongoDB> { Database.db }
    single<PermissionService> { PermissionServiceImpl(get<MongoDB>()) }
    single<RoleService> { RoleServiceImpl(get<MongoDB>(), get<PermissionService>()) }
}