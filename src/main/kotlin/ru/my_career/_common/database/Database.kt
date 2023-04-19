package ru.my_career._common.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.my_career._common.confirmations.tables.ConfirmationsTable
import ru.my_career.companies.tables.CompaniesTable
import ru.my_career.companies.tables.CompaniesUsersRolesTable
import ru.my_career.roles.tables.CommonRoleTitlePermissionTable
import ru.my_career.roles.tables.PermissionsTable
import ru.my_career.roles.tables.RolesPermissionsTable
import ru.my_career.roles.tables.RolesTable
import ru.my_career.users.tables.UsersTable

object DatabaseFactory {
    fun init() {
        Database.connect(hikari())
        initDB()
        transaction {
            SchemaUtils.create(
                PermissionsTable,
                RolesTable,
                RolesPermissionsTable,
                CommonRoleTitlePermissionTable,
                UsersTable,
                CompaniesTable,
                CompaniesUsersRolesTable,
                ConfirmationsTable
            )
        }
    }

    private fun initDB() {
        // database connection is handled from hikari properties
        val config = HikariConfig("/hikari.properties")
        val ds = HikariDataSource(config)
        Database.connect(ds)
        initCustomTypes()
    }

    // database connection for h2 d
    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:file:~/documents/db/h2db"
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }
}
