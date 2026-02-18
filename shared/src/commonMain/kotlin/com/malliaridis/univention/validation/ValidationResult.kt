package com.malliaridis.univention.validation

data class ValidationResult(val errors: List<ValidationError>) {
    val isValid: Boolean get() = errors.isEmpty()

    fun errorMessage(field: String): String? = errors.firstOrNull { it.field == field }?.message
}
