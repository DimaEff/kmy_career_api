package ru.my_career.config

import org.koin.dsl.module
import ru.my_career.common.notifications.NotificationService
import ru.my_career.common.notifications.NotificationServiceImpl

val applicationModule = module {
//    single<MongoDB> { Database.db }
//    single<NotificationService> { NotificationServiceImpl() }
//
//    single<PermissionService> { PermissionServiceImpl(get<MongoDB>()) }
//    single<UsersService> { UsersServiceImpl(get<MongoDB>()) }
//    single<RoleService> { RoleServiceImpl(get<MongoDB>(), get<PermissionService>()) }
//    single<CompanyService> { CompanyServiceImpl(get<MongoDB>(), get<RoleService>(), get<UsersService>()) }
}