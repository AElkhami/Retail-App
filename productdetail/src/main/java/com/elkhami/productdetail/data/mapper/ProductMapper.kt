package com.elkhami.productdetail.data.mapper

import com.elkhami.productdetail.data.dto.ProductResponse
import com.elkhami.productdetail.domain.model.DeliveryInfo
import com.elkhami.productdetail.domain.model.Product
import com.elkhami.productdetail.domain.model.Rating
import com.elkhami.productdetail.domain.model.value.ImageUrl
import com.elkhami.productdetail.domain.model.Brand
import com.elkhami.productdetail.domain.model.InStock
import com.elkhami.productdetail.domain.model.Seller

fun ProductResponse.toDomain(): Product {
    return Product(
        title = title.orEmpty(),
        brand = Brand(
            name = brand?.label.orEmpty()
        ),
        rating = Rating(
            value = averageRating?.rating ?: 0.0,
            count = averageRating?.count ?: 0
        ),
        priceText = price.orEmpty(),
        images = images.orEmpty().map { ImageUrl(it.url.orEmpty()) },
        delivery = DeliveryInfo(
            inStock = InStock(scarcityLevel = delivery?.inStock?.scarcityLevel.orEmpty()),
            description = delivery?.delivery?.description.orEmpty(),
        ),
        seller = Seller(
            name = seller?.displayName.orEmpty(),
        ),
        isSelect = isSelect ?: false
    )
}

