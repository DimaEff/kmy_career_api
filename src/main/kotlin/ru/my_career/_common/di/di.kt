package ru.my_career._common.di

import io.ktor.client.*
import org.koin.dsl.module
import ru.my_career._common.confirmations.repositories.ConfirmationsRepository
import ru.my_career._common.confirmations.services.ConfirmationsService
import ru.my_career._common.confirmations.services.ConfirmationsServiceImpl
import ru.my_career._common.httpClient.httpClient
import ru.my_career._common.notifications.services.NotificationService
import ru.my_career._common.notifications.services.NotificationServiceImpl
import ru.my_career.auth.services.AuthService
import ru.my_career.auth.services.AuthServiceImpl
import ru.my_career.companies.repositories.CompaniesRepository
import ru.my_career.companies.services.CompaniesService
import ru.my_career.companies.services.CompaniesServiceImpl
import ru.my_career.roles.repositories.PermissionsRepository
import ru.my_career.roles.repositories.RolesRepository
import ru.my_career.roles.services.PermissionsService
import ru.my_career.roles.services.PermissionsServiceImpl
import ru.my_career.roles.services.RolesService
import ru.my_career.roles.services.RolesServiceImpl
import ru.my_career._common.users.repositories.UsersRepository
import ru.my_career._common.users.services.UsersService
import ru.my_career._common.users.services.UsersServiceImpl

val applicationModule = module {
    // repository
    single { PermissionsRepository() }
    single { RolesRepository() }
    single { UsersRepository() }
    single { CompaniesRepository() }
    single { ConfirmationsRepository() }

    // common
    single<HttpClient> { httpClient }
    single<NotificationService> { NotificationServiceImpl(get<HttpClient>()) }
    single<ConfirmationsService> {
        ConfirmationsServiceImpl(
            get<ConfirmationsRepository>(),
            get<NotificationService>()
        )
    }
    single<UsersService> { UsersServiceImpl(get<UsersRepository>()) }

    // services
    single<PermissionsService> { PermissionsServiceImpl(get<PermissionsRepository>()) }
    single<RolesService> { RolesServiceImpl(get<RolesRepository>(), get<PermissionsRepository>()) }
    single<CompaniesService> { CompaniesServiceImpl(get<RolesService>(), get<CompaniesRepository>()) }
    single<AuthService> { AuthServiceImpl(get<ConfirmationsService>(), get<UsersService>()) }
}
