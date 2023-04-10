package ru.my_career.companies

import org.koin.dsl.module
import ru.my_career.companies.services.CompanyService
import ru.my_career.companies.services.CompanyServiceImpl
import ru.my_career.config.Database
import ru.my_career.config.MongoDB
import ru.my_career.roles.services.PermissionService
import ru.my_career.roles.services.PermissionServiceImpl
import ru.my_career.roles.services.RoleService
import ru.my_career.roles.services.RoleServiceImpl
import ru.my_career.users.services.UsersService
import ru.my_career.users.services.UsersServiceImpl

val companiesModule = module {
    single<MongoDB> { Database.db }
    single<PermissionService> { PermissionServiceImpl(get<MongoDB>()) }
    single<UsersService> { UsersServiceImpl(get<MongoDB>()) }
    single<RoleService> { RoleServiceImpl(get<MongoDB>(), get<PermissionService>(), get<CompanyService>()) }
    single<CompanyService> { CompanyServiceImpl(get<MongoDB>(), get<RoleService>(), get<UsersService>()) }
}