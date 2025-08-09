package com.elkhami.productdetail.data.datasource

import com.elkhami.core.error.AppError
import com.elkhami.core.util.JsonParser
import com.elkhami.core.util.Result
import com.elkhami.core_android.utils.AssetReader
import com.elkhami.productdetail.data.dto.ProductResponse
import com.elkhami.productdetail.data.mapper.toDomain
import com.elkhami.productdetail.domain.model.Product
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertTrue
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AssetProductDataSourceTest {

    @MockK lateinit var assetReader: AssetReader
    @MockK lateinit var jsonParser: JsonParser

    private lateinit var dataSource: AssetProductDataSource
    private val dispatcher = StandardTestDispatcher()
    private val assetName = "product.json"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = AssetProductDataSource(
            assetReader = assetReader,
            jsonParser = jsonParser,
            dispatcher = dispatcher,
            assetName = assetName
        )
    }

    @Test
    fun `getProductDetails returns Success on happy path`() = runTest(dispatcher) {
        val rawJson = """{"title":"Nintendo Switch Console - Blauw  Rood"}"""
        val dto = mockk<ProductResponse>(relaxed = true)
        val domainProduct = mockk<Product>(relaxed = true)

        coEvery { assetReader.read(assetName) } returns rawJson
        coEvery { jsonParser.parse(rawJson, ProductResponse.serializer()) } returns dto

        // mock the top-level extension
        mockkStatic("com.elkhami.productdetail.data.mapper.ProductMapperKt")
        every { dto.toDomain() } returns domainProduct

        val result = dataSource.getProductDetails()

        assertTrue(result is Result.Success)
        assertSame(domainProduct, (result as Result.Success).data)
    }

    @Test
    fun `getProductDetails maps FileNotFoundException to AppError_FileNotFound`() = runTest(dispatcher) {
        coEvery { assetReader.read(assetName) } throws java.io.FileNotFoundException("missing")

        val result = dataSource.getProductDetails()

        assertTrue(result is Result.Error)
        assertEquals(AppError.FileNotFound, (result as Result.Error).error)
        coVerify(exactly = 1) { assetReader.read(assetName) }
        coVerify(exactly = 0) { jsonParser.parse(any(), any()) }
    }

    @Test
    fun `getProductDetails maps SerializationException to AppError_ParseError`() = runTest(dispatcher) {
        val rawJson = "{}"
        coEvery { assetReader.read(assetName) } returns rawJson
        coEvery { jsonParser.parse(rawJson, ProductResponse.serializer()) } throws
                kotlinx.serialization.SerializationException("bad json")

        val result = dataSource.getProductDetails()

        assertTrue(result is Result.Error)
        assertEquals(AppError.ParseError, (result as Result.Error).error)
        coVerify(exactly = 1) { assetReader.read(assetName) }
        coVerify(exactly = 1) { jsonParser.parse(rawJson, ProductResponse.serializer()) }
    }

    @Test
    fun `getProductDetails maps IllegalArgumentException to AppError_InvalidData`() = runTest(dispatcher) {
        val rawJson = "{}"
        coEvery { assetReader.read(assetName) } returns rawJson
        coEvery { jsonParser.parse(rawJson, ProductResponse.serializer()) } throws
                IllegalArgumentException("invalid")

        val result = dataSource.getProductDetails()

        assertTrue(result is Result.Error)
        assertEquals(AppError.InvalidData, (result as Result.Error).error)
        coVerify(exactly = 1) { assetReader.read(assetName) }
        coVerify(exactly = 1) { jsonParser.parse(rawJson, ProductResponse.serializer()) }
    }

    @Test
    fun `getProductDetails maps unknown Exception to AppError_Unknown`() = runTest(dispatcher) {
        val rawJson = "{}"
        coEvery { assetReader.read(assetName) } returns rawJson
        coEvery { jsonParser.parse(rawJson, ProductResponse.serializer()) } throws
                RuntimeException("boom")

        val result = dataSource.getProductDetails()

        assertTrue(result is Result.Error)
        assertEquals(AppError.Unknown, (result as Result.Error).error)
        coVerify(exactly = 1) { assetReader.read(assetName) }
        coVerify(exactly = 1) { jsonParser.parse(rawJson, ProductResponse.serializer()) }
    }

    @Test(expected = CancellationException::class)
    fun `getProductDetails propagates CancellationException`() = runTest(dispatcher) {
        coEvery { assetReader.read(assetName) } throws CancellationException("cancelled")
        dataSource.getProductDetails()
    }
}
