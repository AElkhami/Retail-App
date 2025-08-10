package com.elkhami.productdetail.presentation

import app.cash.turbine.test
import com.elkhami.core.error.AppError
import com.elkhami.productdetail.domain.usecase.GetProductDetailUseCase
import io.mockk.mockk
import kotlin.test.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import com.elkhami.core.util.Result
import com.elkhami.core_ui.mapper.toUiText
import com.elkhami.core_ui.state.ScreenState
import com.elkhami.core_ui.util.UiText
import com.elkhami.productdetail.domain.model.Brand
import com.elkhami.productdetail.domain.model.DeliveryInfo
import com.elkhami.productdetail.domain.model.InStock
import com.elkhami.productdetail.domain.model.Product
import com.elkhami.productdetail.domain.model.Rating
import com.elkhami.productdetail.domain.model.Seller
import com.elkhami.productdetail.domain.model.value.ImageUrl
import com.elkhami.productdetail.presentation.effect.ProductDetailEffect
import com.elkhami.productdetail.presentation.event.ProductDetailEvent
import com.elkhami.productdetail.presentation.mapper.toUiContent
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    private val getProductDetailUseCase: GetProductDetailUseCase = mockk()
    private lateinit var viewModel: ProductDetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductDetailViewModel(getProductDetailUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getProductDetails emits Loading then Content on success`() = runTest {
        // Given
        val product = sampleProduct() // fake domain model
        coEvery { getProductDetailUseCase() } returns Result.Success(product)

        // When
        viewModel.getProductDetails()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val states = viewModel.uiState.value
        assertThat(states.screenState).isInstanceOf(ScreenState.Content::class.java)
        val content = states.screenState as ScreenState.Content
        assertThat(content.data).isEqualTo(product.toUiContent())
    }

    @Test
    fun `getProductDetails emits Loading then Error on failure`() = runTest {
        val error = AppError.Unknown
        coEvery { getProductDetailUseCase() } returns Result.Error(error)

        viewModel.getProductDetails()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value.screenState
        assertThat(state).isInstanceOf(ScreenState.Error::class.java)

        val actual = (state as ScreenState.Error).message as UiText.StringResource
        val expected = error.toUiText() as UiText.StringResource

        assertThat(actual.resId).isEqualTo(expected.resId)
        assertThat(actual.args.toList()).isEqualTo(expected.args.toList())
    }

    @Test
    fun `onEvent BackClicked emits NavigateBack effect`() = runTest {
        viewModel.effect.test {
            viewModel.onEvent(ProductDetailEvent.BackClicked)
            assertThat(awaitItem()).isEqualTo(ProductDetailEffect.NavigateBack)
        }
    }

    @Test
    fun `onEvent ShareClicked emits ShareProduct effect`() = runTest {
        viewModel.effect.test {
            viewModel.onEvent(ProductDetailEvent.ShareClicked)
            assertThat(awaitItem()).isEqualTo(ProductDetailEffect.ShareProduct)
        }
    }

    @Test
    fun `onEvent OnBrandClick emits NavigateToBrand effect`() = runTest {
        viewModel.effect.test {
            viewModel.onEvent(ProductDetailEvent.BrandClicked)
            assertThat(awaitItem()).isEqualTo(ProductDetailEffect.NavigateToBrand)
        }
    }

    @Test
    fun `onEvent Retry calls getProductDetails`() = runTest {
        coEvery { getProductDetailUseCase() } returns Result.Success(sampleProduct())

        viewModel.onEvent(ProductDetailEvent.RetryClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.uiState.value.screenState)
            .isInstanceOf(ScreenState.Content::class.java)
    }

    private fun sampleProduct(): Product {
        return Product(
            title = "Sample Product",
            brand = Brand("BrandName"),
            rating = Rating(4.5, 10),
            priceText = "$9.99",
            images = listOf(ImageUrl("https://image.com")),
            delivery = DeliveryInfo(InStock(scarcityLevel = "instock"),"description"),
            seller = Seller("SellerName"),
            isSelect = false
        )
    }
}