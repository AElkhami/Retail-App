package com.elkhami.productdetail.domain.usecase

import com.elkhami.core.util.Result
import com.elkhami.productdetail.domain.model.*
import com.elkhami.productdetail.domain.model.value.ImageUrl
import com.elkhami.productdetail.domain.repository.ProductDetailRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetProductDetailUseCaseTest {

    private val repository: ProductDetailRepository = mockk()
    private val useCase = GetProductDetailUseCase(repository)

    @Test
    fun `invoke returns Success when repository succeeds`() = runTest {
        val product = sampleProduct()
        coEvery { repository.getProductDetails() } returns Result.Success(product)

        val result = useCase()

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat((result as Result.Success).data).isEqualTo(product)
        coVerify(exactly = 1) { repository.getProductDetails() }
    }

    @Test
    fun `invoke returns Error when repository fails`() = runTest {
        val error = com.elkhami.core.error.AppError.Unknown
        coEvery { repository.getProductDetails() } returns Result.Error(error)

        val result = useCase()

        assertThat(result).isInstanceOf(Result.Error::class.java)
        assertThat((result as Result.Error).error).isEqualTo(error)
        coVerify(exactly = 1) { repository.getProductDetails() }
    }

    private fun sampleProduct() = Product(
        title = "Sample",
        brand = Brand("Brand"),
        rating = Rating(4.5, 10),
        priceText = "99",
        images = listOf(ImageUrl("https://img")),
        delivery = DeliveryInfo(InStock("in_stock"), "desc"),
        seller = Seller("Seller"),
        isSelect = false
    )
}
