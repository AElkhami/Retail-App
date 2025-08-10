package com.elkhami.productdetail.data.di

import android.content.Context
import com.elkhami.core.util.JsonParser
import com.elkhami.core_android.utils.AssetReader
import com.elkhami.productdetail.data.datasource.AssetProductDataSource
import com.elkhami.productdetail.data.datasource.ProductDataSource
import com.elkhami.productdetail.data.repository.AssetProductDetailRepository
import com.elkhami.productdetail.domain.repository.ProductDetailRepository
import com.elkhami.productdetail.data.di.qualifier.ResponseAssetName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    @ResponseAssetName
    fun provideResponseAssetName(): String = "product.json"

    @Provides
    @Singleton
    fun provideAssetReader(
        @ApplicationContext
        context: Context
    ): AssetReader = AssetReader(context.assets)

    @Provides
    @Singleton
    fun provideProductDataSource(
        assetReader: AssetReader,
        jsonParser: JsonParser,
        @ResponseAssetName assetName: String
    ): ProductDataSource =
        AssetProductDataSource(
            assetReader = assetReader,
            jsonParser = jsonParser,
            dispatcher = Dispatchers.IO,
            assetName = assetName
        )

    @Provides
    @Singleton
    fun provideProductRepository(
        dataSource: ProductDataSource,
    ): ProductDetailRepository = AssetProductDetailRepository(dataSource)
}