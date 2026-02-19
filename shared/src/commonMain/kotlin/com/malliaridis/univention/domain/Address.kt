package com.malliaridis.univention.domain

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val street: String = "",
    val houseNumber: String = "",
    val zipCode: String = "",
    val city: String = "",
    // TODO Consider additional fields including:
    //  - Country
    //  - additional address lines for Floor number / Name on doorbell / Other
)
