package ru.my_career.common.database

import ru.my_career.common.database.queries.createNewType
import ru.my_career.roles.CommonRoleTitle

/***
 The function must be calling before the initialization of main tables.
 ***/
fun initCustomTypes() {
    initCommonRoleTitleType()
}

private fun initCommonRoleTitleType() {
    createNewType(CommonRoleTitle)
}
