package com.elkhami.productdetail.data.datasource

import com.elkhami.core.error.AppError
import com.elkhami.core.util.JsonParser
import com.elkhami.core.util.Result
import com.elkhami.core_android.utils.AssetReader
import com.elkhami.productdetail.data.dto.ProductResponse
import com.elkhami.productdetail.data.mapper.toDomain
import com.elkhami.productdetail.domain.model.Product
import com.elkhami.productdetail.data.di.qualifier.ResponseAssetName
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class AssetProductDataSource(
    private val assetReader: AssetReader,
    private val jsonParser: JsonParser,
    private val dispatcher: CoroutineDispatcher,
    @param:ResponseAssetName private val assetName: String
) : ProductDataSource {
    override suspend fun getProductDetails(): Result<Product> {
        return withContext(dispatcher) {
            runCatching {
                val raw = assetReader.read(assetName)

                Timber.d("Raw JSON loaded: ${raw.length} characters")

                val productResponse = jsonParser.parse(raw, ProductResponse.serializer())

                Result.Success(productResponse.toDomain())
            }.getOrElse { throwable ->
                when (throwable) {
                    is CancellationException -> throw throwable
                    is java.io.FileNotFoundException -> {
                        Timber.e("Asset file not found: $assetName")
                        Result.Error(AppError.FileNotFound)
                    }

                    is kotlinx.serialization.SerializationException -> {
                        Timber.e("JSON parsing failed: ${throwable.message}")
                        Result.Error(AppError.ParseError)
                    }

                    is IllegalArgumentException -> {
                        Timber.e("Invalid file: ${throwable.message}")
                        Result.Error(AppError.InvalidData)
                    }

                    else -> {
                        Timber.e("Unknown error: ${throwable.message}")
                        Result.Error(AppError.Unknown)
                    }
                }
            }
        }
    }
}