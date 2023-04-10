package ru.my_career.roles.services

import org.bson.types.ObjectId
import ru.my_career.config.MongoDB
import ru.my_career.roles.models.CreatePermissionDto
import ru.my_career.roles.models.Permission

class PermissionServiceImpl(db: MongoDB) : PermissionService {
    private val permissionRepository = db.getCollection<Permission>()

    override suspend fun getPermissionById(id: String?): Permission? {
        if (id == null) {
            return null
        }

        val p = permissionRepository.findOneById(ObjectId(id))
        println(p)
        return p
    }

    override suspend fun deletePermissionById(id: String) {
        permissionRepository.deleteOneById(ObjectId(id))
    }

    override suspend fun createPermission(dto: CreatePermissionDto): Permission? {
        return try {
            val permission = Permission(title = dto.title, description = dto.description)
            permissionRepository.insertOne(permission)
            permission
        } catch (e: Throwable) {
            null
        }
    }

    override suspend fun getPermissionsByIds(ids: Collection<String>): Collection<Permission> {
        val objectsIds = ids.map { "ObjectId('$it')" }
        val queryString = "{ \"_id\": { \$in: $objectsIds } }"
        return permissionRepository.find(queryString).toList()
    }

}