package com.malliaridis.univention.database.dao

import com.malliaridis.univention.database.tables.AddressesTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.UuidEntity
import org.jetbrains.exposed.v1.dao.UuidEntityClass
import kotlin.uuid.Uuid

class AddressEntity(id: EntityID<Uuid>) : UuidEntity(id) {
    companion object : UuidEntityClass<AddressEntity>(AddressesTable)

    var street by AddressesTable.street
    var houseNumber by AddressesTable.houseNumber
    var zipCode by AddressesTable.zipCode
    var city by AddressesTable.city
}
