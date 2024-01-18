package de.imedia24.shop.controller

import de.imedia24.shop.domain.product.PartialProductRequest
import de.imedia24.shop.domain.product.ProductRequest
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(private val productService: ProductService) {

    private val logger = LoggerFactory.getLogger(ProductController::class.java)!!

    @GetMapping("/products/{sku}", produces = ["application/json;charset=utf-8"])
    fun findProductBySku(
        @PathVariable("sku") sku: String
    ): ResponseEntity<ProductResponse> {
        logger.info("Request for product $sku")

        val product = productService.findProductBySku(sku)
        return if (product == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(product)
        }
    }

    @GetMapping("/products", produces = ["application/json;charset=utf-8"])
    fun findProductsBySkus(
        @RequestParam("skus") skus: String
    ): ResponseEntity<List<ProductResponse>> {
        logger.info("Request for products $skus")

        val products = productService.findProductsBySkus(skus)
        return if (products.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(products)
        }
    }

    @PostMapping(
        "/products",
        consumes = ["application/json;charset=utf-8"],
        produces = ["application/json;charset=utf-8"]
    )
    fun addProduct(
        @RequestBody productRequest: ProductRequest
    ): ResponseEntity<ProductResponse> {
        logger.info("Request to add product $productRequest")

        val addedProduct = productService.addProduct(productRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct)
    }

    @PatchMapping(
        "/products/{sku}",
        consumes = ["application/json;charset=utf-8"],
        produces = ["application/json;charset=utf-8"]
    )
    fun partiallyUpdateProduct(
        @PathVariable("sku") sku: String,
        @RequestBody partialProductRequest: PartialProductRequest
    ): ResponseEntity<ProductResponse> {
        logger.info("Request to partially update product $sku with $partialProductRequest")

        val updatedProduct = productService.partiallyUpdateProduct(sku, partialProductRequest)
        return if (updatedProduct == null) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(updatedProduct)
        }
    }
}
