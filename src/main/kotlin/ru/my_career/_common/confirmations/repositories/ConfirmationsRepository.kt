package ru.my_career._common.confirmations.repositories

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import ru.my_career._common.database.Id
import ru.my_career._common.confirmations.dto.AddCheckConfirmationDto
import ru.my_career._common.confirmations.tables.ConfirmationsTable

class ConfirmationsRepository {
    fun addConfirmation(dto: AddCheckConfirmationDto): Unit? {
        transaction {
            ConfirmationDao.new {
                confirmationSubject = dto.confirmationSubject
                code = dto.code
            }
        }
        return Unit
    }

    fun getConfirmationBySubject(confirmationSubject: String): ConfirmationDao? {
        return ConfirmationDao.find { ConfirmationsTable.confirmationSubject eq confirmationSubject }.limit(1)
            .firstOrNull()
    }

    fun setConfirmed(confirmation: ConfirmationDao): Unit = transaction {
        confirmation.isConfirmed = true
    }
}

class ConfirmationDao(id: EntityID<Id>): IntEntity(id) {
    companion object: IntEntityClass<ConfirmationDao>(ConfirmationsTable)
    var confirmationSubject by ConfirmationsTable.confirmationSubject
    var code by ConfirmationsTable.code
    var isConfirmed by ConfirmationsTable.isConfirmed
}
