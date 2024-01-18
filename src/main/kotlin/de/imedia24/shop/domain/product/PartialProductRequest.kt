package de.imedia24.shop.domain.product


import java.math.BigDecimal

data class PartialProductRequest(
    val name: String?,
    val description: String?,
    val price: BigDecimal?
)

