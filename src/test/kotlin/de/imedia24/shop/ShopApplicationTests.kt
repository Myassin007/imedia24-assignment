package de.imedia24.shop

import de.imedia24.shop.controller.ProductController
import de.imedia24.shop.domain.product.PartialProductRequest
import de.imedia24.shop.domain.product.ProductResponse
import de.imedia24.shop.service.ProductService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import java.math.BigDecimal


@SpringBootTest
class ShopApplicationTests {
    private val productServiceMock = mock(ProductService::class.java)
    private val productController = ProductController(productServiceMock)

    @Test
    fun contextLoads() {
    }

    @Test
    fun `test retrieveProductBySku with valid product`() {
        val sku = "789"
        val productResponse = ProductResponse(sku, "New Product", "New Description", BigDecimal.valueOf(49.99), 10)

        `when`(productServiceMock.findProductBySku(sku)).thenReturn(productResponse)

        val result = productController.findProductBySku(sku)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == productResponse)
    }

    @Test
    fun `test retrieveProductBySku with invalid product`() {
        val sku = "101112"

        `when`(productServiceMock.findProductBySku(sku)).thenReturn(null)

        val result = productController.findProductBySku(sku)

        assert(result.statusCode == HttpStatus.NOT_FOUND)
    }

    @Test
    fun `test updateProductPartially with valid product`() {
        val sku = "789"
        val partialProductRequest =
            PartialProductRequest("Newer Name", "Newer Description", BigDecimal.valueOf(59.99))
        val updatedProductResponse =
            ProductResponse(sku, "Newer Name", "Newer Description", BigDecimal.valueOf(59.99), 10)

        `when`(productServiceMock.partiallyUpdateProduct(sku, partialProductRequest)).thenReturn(updatedProductResponse)

        val result = productController.partiallyUpdateProduct(sku, partialProductRequest)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == updatedProductResponse)
    }

    @Test
    fun `test updateProductPartially with invalid product`() {
        val sku = "101112"
        val partialProductRequest =
            PartialProductRequest("Newer Name", "Newer Description", BigDecimal.valueOf(59.99))

        `when`(productServiceMock.partiallyUpdateProduct(sku, partialProductRequest)).thenReturn(null)

        val result = productController.partiallyUpdateProduct(sku, partialProductRequest)

        assert(result.statusCode == HttpStatus.NOT_FOUND)
    }
}

