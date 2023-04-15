package ru.my_career.config

import org.koin.dsl.module
import ru.my_career.common.notifications.NotificationService
import ru.my_career.common.notifications.NotificationServiceImpl
import ru.my_career.companies.services.CompanyService
import ru.my_career.companies.services.CompanyServiceImpl
import ru.my_career.roles.services.PermissionService
import ru.my_career.roles.services.PermissionServiceImpl
import ru.my_career.roles.services.RoleService
import ru.my_career.roles.services.RoleServiceImpl
import ru.my_career.users.services.UsersService
import ru.my_career.users.services.UsersServiceImpl

val applicationModule = module {
    single<MongoDB> { Database.db }
    single<NotificationService> { NotificationServiceImpl() }

    single<PermissionService> { PermissionServiceImpl(get<MongoDB>()) }
    single<UsersService> { UsersServiceImpl(get<MongoDB>()) }
    single<RoleService> { RoleServiceImpl(get<MongoDB>(), get<PermissionService>()) }
    single<CompanyService> { CompanyServiceImpl(get<MongoDB>(), get<RoleService>(), get<UsersService>()) }
}