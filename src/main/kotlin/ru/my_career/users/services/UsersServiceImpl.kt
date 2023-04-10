package ru.my_career.users.services

import org.bson.types.ObjectId
import ru.my_career.common.db.MongoId
import ru.my_career.config.MongoDB
import ru.my_career.users.models.CreateUserDto
import ru.my_career.users.models.User

class UsersServiceImpl(db: MongoDB): UsersService {
    private val usersRepository = db.getCollection<User>()

    override suspend fun createUser(dto: CreateUserDto): User? {
        return try {
            val user = User(
                name = dto.name,
                surname = dto.surname,
                phone = dto.phone,
                email = dto.email
            )
            usersRepository.insertOne(user)
            user
        } catch (e: Throwable) {
            null
        }
    }

    override suspend fun getUserById(userId: String): User? {
        return usersRepository.findOneById(ObjectId(userId))
    }
}