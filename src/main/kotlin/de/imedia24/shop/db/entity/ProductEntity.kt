package de.imedia24.shop.db.entity

import org.hibernate.annotations.UpdateTimestamp
import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "products")
public data class ProductEntity(
    @Id
    @Column(name = "sku", nullable = false)
    val sku: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description")
    val description: String? = null,

    @Column(name = "price", nullable = false)
    val price: BigDecimal,

    @UpdateTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: ZonedDateTime,

    @Column(name = "stock_quantity", nullable = false)
    val stockQuantity: Int

) {
    constructor() : this("", "", null, BigDecimal.ZERO, ZonedDateTime.now(), ZonedDateTime.now(), 0)

    fun copy(
        sku: String = this.sku,
        name: String = this.name,
        description: String? = this.description,
        price: BigDecimal = this.price,
        stockQuantity: Int = this.stockQuantity
    ): ProductEntity {
        return ProductEntity(sku, name, description, price, this.createdAt, this.updatedAt, stockQuantity)
    }


}
