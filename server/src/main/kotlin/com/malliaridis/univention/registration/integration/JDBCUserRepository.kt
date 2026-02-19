package com.malliaridis.univention.registration.integration

import com.malliaridis.univention.database.dao.AddressEntity
import com.malliaridis.univention.database.dao.UserEntity
import com.malliaridis.univention.dto.CreateUserRequestDto
import com.malliaridis.univention.registration.UserRepository
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import kotlin.uuid.Uuid

class JDBCUserRepository : UserRepository {
    override fun createUser(user: CreateUserRequestDto): Result<Uuid> = runCatching {
        val user = transaction {
            UserEntity.new {
                firstName = user.firstName
                lastName = user.lastName
                phoneNumber = user.phoneNumber
                username = user.username
                address = AddressEntity.new {
                    street = user.address.street
                    houseNumber = user.address.houseNumber
                    city = user.address.city
                    zipCode = user.address.zipCode
                }
            }
        }
        return Result.success(user.id.value)
    }
}
