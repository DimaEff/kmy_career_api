package ru.my_career._common.users.repositories

import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import ru.my_career._common.database.Id
import ru.my_career._common.users.dto.CreateUserDto
import ru.my_career._common.users.tables.UsersTable

class UsersRepository {
    private val logger = LoggerFactory.getLogger(UsersRepository::class.java)

    fun createUser(dto: CreateUserDto): UserDao? = try {
        transaction {
            UserDao.new {
                firstname = dto.firstName
                surname = dto.surname
                phoneNumber = dto.phoneNumber
                email = dto.email
            }
        }
    } catch (e: Throwable) {
        logger.error("Failed createUser: ${e.message}")
        null
    }

    fun getUser(id: Id): UserDao? = transaction { UserDao.findById(id) }
    fun getUsersByIds(ids: Collection<Id>): Collection<UserDao>? {
        var users: Collection<UserDao>? = null
        transaction {
            users = UsersTable.select { UsersTable.id inList ids }.map { UserDao.wrapRow(it) }
        }

        return users
    }

    fun getUserByPhoneNumber(phoneNumber: String): UserDao? = transaction {
        UsersTable.select { UsersTable.phoneNumber eq phoneNumber }.limit(1).firstOrNull()?.let { UserDao.wrapRow(it) }
    }
}