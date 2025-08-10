package com.elkhami.productdetail.domain.di

import com.elkhami.productdetail.domain.repository.ProductDetailRepository
import com.elkhami.productdetail.domain.usecase.GetProductDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object DomainModule {
    @Provides
    @ViewModelScoped
    fun provideProductDetailUseCase(
        repository: ProductDetailRepository
    ) = GetProductDetailUseCase(repository)
}