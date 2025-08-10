package com.elkhami.productdetail.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val title: String?,
    val brand: Brand?,
    val averageRating: AvgRating?,
    val price: String?,
    val images: List<Image>?,
    val delivery: DeliveryWrapper?,
    val seller: Seller?,
    val isSelect: Boolean?
) {
    @Serializable
    data class Brand(val id: String?, val label: String?)
    @Serializable
    data class AvgRating(val rating: Double?, val count: Int?)
    @Serializable
    data class Image(val url: String?)
    @Serializable
    data class DeliveryWrapper(
        @SerialName("instock")
        val inStock: InStock?,
        val delivery: Delivery?
    ) {
        @Serializable
        data class InStock(val scarcityLevel: String?)
        @Serializable
        data class Delivery(val description: String?, val info: Info?) {
            @Serializable
            data class Info(val title: String?, val htmlText: String?)
        }
    }
    @Serializable
    data class Seller(val id: String?, val displayName: String?, val rating: Double?, val topSeller: Boolean?)
}
