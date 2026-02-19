package com.malliaridis.univention.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserResponseDto(
    val userId: String,
)
