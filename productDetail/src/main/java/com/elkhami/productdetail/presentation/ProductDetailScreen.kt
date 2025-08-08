package com.elkhami.productdetail.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProductDetailScreen() {
    ProductDetailScreenContent()
}

@Composable
fun ProductDetailScreenContent() {

}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    RetailAppTheme {
        ProductDetailScreenContent()
    }
}