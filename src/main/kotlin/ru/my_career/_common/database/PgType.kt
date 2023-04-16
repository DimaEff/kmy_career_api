package ru.my_career._common.database

import com.sun.jdi.InvalidTypeException

abstract class PgType<T : Enum<T>> {
    abstract val className: String?
    abstract val values: Collection<String>
    abstract fun getValueBy(value: Any): T

    val typeName: String
        get() = className ?: throw InvalidTypeException("The className must be a String, found - null")

    val sqlValues: String
        get() = values.joinToString(separator = ", ") { "'${it}'" }

}