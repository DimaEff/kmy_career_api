package ru.my_career.roles

import ru.my_career._common.database.PgType

// https://github.com/DimaEff/my_career_api1/wiki/1.-%D0%9A%D0%BE%D0%BC%D0%BF%D0%B0%D0%BD%D0%B8%D0%B8.-%D0%A1%D0%BE%D1%82%D1%80%D1%83%D0%B4%D0%BD%D0%B8%D0%BA%D0%B8.-%D0%A0%D0%BE%D0%BB%D0%B8#%D0%BE%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5-%D1%81%D1%82%D0%B0%D0%BD%D0%B4%D0%B0%D1%80%D1%82%D0%BD%D1%8B%D1%85-%D1%80%D0%BE%D0%BB%D0%B5%D0%B9
enum class CommonRoleTitle {
    OWNER,
    MASTER,
    MECHANIC,
    MANAGER,
    DISPATCHER,
    ACCOUNTANT;

    companion object : PgType<CommonRoleTitle>() {
        override val className = CommonRoleTitle::class.simpleName
        override val values = CommonRoleTitle.values().map { it.toString() }
        override fun getValueBy(value: Any) = valueOf(value as String)
    }
}
