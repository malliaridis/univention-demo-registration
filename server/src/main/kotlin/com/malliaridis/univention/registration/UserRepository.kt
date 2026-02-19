package com.malliaridis.univention.registration

import com.malliaridis.univention.dto.CreateUserRequestDto
import kotlin.uuid.Uuid

interface UserRepository {
    fun createUser(user: CreateUserRequestDto): Result<Uuid>
}
