package com.malliaridis.univention.database.dao

import com.malliaridis.univention.database.tables.UsersTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.UuidEntity
import org.jetbrains.exposed.v1.dao.UuidEntityClass
import kotlin.uuid.Uuid

class UserEntity(id: EntityID<Uuid>) : UuidEntity(id) {
    companion object : UuidEntityClass<UserEntity>(UsersTable)

    var firstName by UsersTable.firstName
    var lastName by UsersTable.lastName
    var phoneNumber by UsersTable.phoneNumber
    var username by UsersTable.username
    var address by AddressEntity referencedOn UsersTable.address
}
