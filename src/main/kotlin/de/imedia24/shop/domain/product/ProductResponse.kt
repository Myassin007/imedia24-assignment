package de.imedia24.shop.domain.product

import de.imedia24.shop.db.entity.ProductEntity
import java.math.BigDecimal

data class ProductResponse(
    val sku: String,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stock: Int
) {
    companion object {
        fun ProductEntity.toProductResponse() = ProductResponse(
            sku = sku,
            name = name,
            description = description ?: "",
            price = price,
            stock = stockQuantity
        )
    }


    fun toProductResponseWithStock(stock: Int): ProductResponse {
        return ProductResponse(
            sku = this.sku,
            name = this.name,
            description = this.description,
            price = this.price,
            stock = stock
        )
    }
}
