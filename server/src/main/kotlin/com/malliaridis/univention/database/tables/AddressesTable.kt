package com.malliaridis.univention.database.tables

import com.malliaridis.univention.database.MAX_VARCHAR_LENGTH
import org.jetbrains.exposed.v1.core.dao.id.UuidTable

object AddressesTable : UuidTable(name = "addresses") {
    val street = varchar(name = "street", length = MAX_VARCHAR_LENGTH)
    val houseNumber = varchar(name = "house_number", length = MAX_VARCHAR_LENGTH)
    val zipCode = varchar(name = "zip_code", length = MAX_VARCHAR_LENGTH)
    val city = varchar(name = "city", length = MAX_VARCHAR_LENGTH)
}
