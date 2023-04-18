package ru.my_career._common.di

import org.koin.dsl.module
import ru.my_career.companies.repositories.CompaniesRepository
import ru.my_career.companies.services.CompaniesService
import ru.my_career.companies.services.CompaniesServiceImpl
import ru.my_career.roles.repositories.PermissionsRepository
import ru.my_career.roles.repositories.RolesRepository
import ru.my_career.roles.services.PermissionsService
import ru.my_career.roles.services.PermissionsServiceImpl
import ru.my_career.roles.services.RolesService
import ru.my_career.roles.services.RolesServiceImpl
import ru.my_career.users.repositories.UsersRepository
import ru.my_career.users.services.UsersService
import ru.my_career.users.services.UsersServiceImpl

val applicationModule = module {
    // repository
    single { PermissionsRepository() }
    single { RolesRepository() }
    single { UsersRepository() }
    single { CompaniesRepository() }

    // services
    single<PermissionsService> { PermissionsServiceImpl(get<PermissionsRepository>()) }
    single<RolesService> { RolesServiceImpl(get<RolesRepository>(), get<PermissionsRepository>()) }
    single<UsersService> { UsersServiceImpl(get<UsersRepository>()) }
    single<CompaniesService> { CompaniesServiceImpl(get<RolesService>(), get<CompaniesRepository>()) }
}
