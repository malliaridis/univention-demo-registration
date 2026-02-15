package com.malliaridis.univention.domain

data class Address(
    val street: String = "",
    val houseNumber: String = "",
    val zipCode: String = "",
    val city: String = "",
)
