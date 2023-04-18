package ru.my_career.users.repositories

import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.my_career._common.database.Id
import ru.my_career.users.dto.CreateUserDto

class UsersRepository {
    private val logger = LoggerFactory.getLogger(UsersRepository::class.java)

    fun createUser(dto: CreateUserDto): UserDao? = try {
        transaction {
            UserDao.new {
                firstname = dto.firstname
                surname = dto.surname
                phoneNumber = dto.phoneNumber
                email = dto.email
            }
        }
    } catch (e: Throwable) {
        logger.error("Failed createUser: ${e.message}")
        null
    }

    fun getUser(id: Id): UserDao? = UserDao.findById(id)
}