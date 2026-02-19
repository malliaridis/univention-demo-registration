package com.malliaridis.univention.database.tables

import com.malliaridis.univention.MAX_NAME_LENGTH
import com.malliaridis.univention.USERNAME_RANGE
import com.malliaridis.univention.database.MAX_VARCHAR_LENGTH
import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object UsersTable : UuidTable(name = "users") {
    val firstName = varchar(name = "first_name", length = MAX_NAME_LENGTH)
    val lastName = varchar(name = "last_name", length = MAX_NAME_LENGTH)
    val phoneNumber = varchar(name = "phone_number", length = MAX_VARCHAR_LENGTH)
    val username = varchar(name = "username", length = USERNAME_RANGE.last)
    val address = reference(name = "address", foreign = AddressesTable)
}
