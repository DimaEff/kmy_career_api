package ru.my_career.users.repositories

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import ru.my_career._common.database.Id
import ru.my_career.users.dto.UserDto
import ru.my_career.users.tables.UsersTable

class UserDao(id: EntityID<Id>): IntEntity(id) {
    companion object : IntEntityClass<UserDao>(UsersTable)

    var firstname by UsersTable.firstname
    var surname by UsersTable.surname
    var phoneNumber by UsersTable.phoneNumber
    var email by UsersTable.email
}

fun UserDao.toDto(): UserDto = UserDto(
    id = this.id.value,
    firstname = this.firstname,
    surname = this.surname,
    phoneNumber = this.phoneNumber,
    email = this.email
)
