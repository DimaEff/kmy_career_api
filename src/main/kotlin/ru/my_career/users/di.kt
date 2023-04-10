package ru.my_career.users

import org.koin.dsl.module
import ru.my_career.config.Database
import ru.my_career.config.MongoDB
import ru.my_career.users.services.UsersService
import ru.my_career.users.services.UsersServiceImpl

val usersModule = module {
    single<MongoDB> { Database.db }
    single<UsersService> { UsersServiceImpl(get<MongoDB>()) }
}