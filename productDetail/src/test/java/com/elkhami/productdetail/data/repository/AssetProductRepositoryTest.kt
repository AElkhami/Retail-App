package com.elkhami.productdetail.data.repository

import com.elkhami.core.error.AppError
import com.elkhami.core.util.Result
import com.elkhami.productdetail.data.datasource.ProductDataSource
import com.elkhami.productdetail.domain.model.Product
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class AssetProductRepositoryTest {
    @MockK lateinit var dataSource: ProductDataSource
    private lateinit var repo: AssetProductRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repo = AssetProductRepository(dataSource)
    }

    @Test
    fun `getProductDetails delegates and returns Success`() = runTest {
        val product: Product = mockk(relaxed = true)
        coEvery { dataSource.getProductDetails() } returns Result.Success(product)

        val result = repo.getProductDetails()

        when (result) {
            is Result.Success -> assertSame(product, result.data)
            is Result.Error -> fail("Expected Success, got Error(${result.error})")
        }
        coVerify(exactly = 1) { dataSource.getProductDetails() }
    }

    @Test
    fun `getProductDetails delegates and returns Error`() = runTest {
        val error = AppError.ParseError
        coEvery { dataSource.getProductDetails() } returns Result.Error(error)

        val result = repo.getProductDetails()

        when (result) {
            is Result.Success -> fail("Expected Error, got Success")
            is Result.Error -> assertEquals(error, result.error)
        }
        coVerify(exactly = 1) { dataSource.getProductDetails() }
    }

    @Test
    fun `getProductDetails propagates CancellationException`() = runTest {
        coEvery { dataSource.getProductDetails() } throws CancellationException("cancelled")

        try {
            repo.getProductDetails()
            fail("Expected CancellationException")
        } catch (_: CancellationException) {
            // expected
        }
        coVerify(exactly = 1) { dataSource.getProductDetails() }
    }
}