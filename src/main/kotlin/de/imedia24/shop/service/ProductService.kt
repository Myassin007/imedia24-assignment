package de.imedia24.shop.service

import de.imedia24.shop.db.entity.ProductEntity
import de.imedia24.shop.db.repository.ProductRepository
import de.imedia24.shop.domain.product.PartialProductRequest
import de.imedia24.shop.domain.product.ProductRequest
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.domain.product.ProductResponse.Companion.toProductResponse
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun findProductBySku(sku: String): ProductResponse? {
        val productEntity = productRepository.findBySku(sku)
        return productEntity?.toProductResponse()
    }

    fun findProductsBySkus(skus: String): List<ProductResponse> {
        val listOfSkuIds = skus.split(",").map { it.trim() }
        val listOfProducts = mutableListOf<ProductEntity>()

        listOfSkuIds.forEach { sku ->
            productRepository.findBySku(sku)?.let {
                listOfProducts.add(it)
            }
        }

        return listOfProducts.mapNotNull { it.toProductResponse() }
    }


    fun addProduct(productRequest: ProductRequest): ProductResponse {
        val newProductEntity = productRepository.save(
            ProductEntity(
                sku = productRequest.sku,
                name = productRequest.name,
                description = productRequest.description,
                price = productRequest.price,
                stockQuantity = productRequest.stock,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )
        )
        return newProductEntity.toProductResponse()
    }

    fun partiallyUpdateProduct(sku: String, partialProductRequest: PartialProductRequest): ProductResponse? {
        var existingProduct = productRepository.findBySku(sku) ?: return null


        partialProductRequest.name?.let { existingProduct = existingProduct.copy(name = it) }
        partialProductRequest.description?.let { existingProduct = existingProduct.copy(description = it) }
        partialProductRequest.price?.let { existingProduct = existingProduct.copy(price = it) }

        // Save the updated product
        val updatedProductEntity = productRepository.save(existingProduct)

        return updatedProductEntity.toProductResponse()
    }

}
