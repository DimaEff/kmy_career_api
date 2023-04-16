package ru.my_career.common.database.queries

import org.jetbrains.exposed.sql.transactions.transaction
import ru.my_career.common.database.PgType

fun <T : Enum<T>>createNewType(type: PgType<T>): Unit? {
    return run {
        transaction {
            exec("""
                DO $$ BEGIN
                    CREATE TYPE ${type.typeName} AS ENUM (${type.sqlValues});
                EXCEPTION
                     WHEN duplicate_object THEN null;
                END ${'$'}${'$'};
            """)
        }
    }
}
