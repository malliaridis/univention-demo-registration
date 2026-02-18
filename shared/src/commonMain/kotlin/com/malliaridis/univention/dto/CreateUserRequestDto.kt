package com.malliaridis.univention.dto

import com.malliaridis.univention.domain.Address
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequestDto(
    val firstName: String,
    val lastName: String,
    val username: String,
    val phoneNumber: String,
    val address: Address,
)
