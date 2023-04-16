package ru.my_career._common.database.custom_db_types

import org.jetbrains.exposed.sql.Table
import ru.my_career._common.database.PGEnum
import ru.my_career._common.database.PgType

fun <T : Enum<T>>Table.enumType(name: String, Type: PgType<T>) = this.customEnumeration(
    name,
    Type.typeName,
    { Type.getValueBy(it) },
    { PGEnum(Type.typeName, it) }
)
