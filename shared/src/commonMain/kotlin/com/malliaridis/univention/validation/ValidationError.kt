package com.malliaridis.univention.validation

import kotlinx.serialization.Serializable

@Serializable
data class ValidationError(val field: String, val message: String)
