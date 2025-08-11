package com.elkhami.core_ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.imageLoader
import com.elkhami.core_ui.ui.theme.AppTheme
import com.elkhami.core_ui.ui.theme.LocalAppColors
import com.elkhami.core_ui.ui.theme.LocalAppDimens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarouselWithIndicator(
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader = LocalContext.current.imageLoader,
    dotSize: Int = 8,
    dotSpacing: Int = 6
) {
    val dimens = LocalAppDimens.current
    val colors = LocalAppColors.current

    val safeImages = remember(imageUrls) { imageUrls.filter { it.isNotBlank() } }
    if (safeImages.isEmpty()) return

    val pagerState = rememberPagerState(pageCount = { safeImages.size })
    val dotsListState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = "Image carousel" },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            OnlineImage(
                imageUrl = safeImages[page],
                modifier = Modifier.fillMaxWidth(),
                imageLoader = imageLoader
            )
        }

        Spacer(Modifier.height(12.dp))

        // Keep the selected dot visible
        LaunchedEffect(pagerState.currentPage) {
            val target = pagerState.currentPage.coerceIn(0, safeImages.lastIndex)
            dotsListState.animateScrollToItem(target)
        }

        LazyRow(
            state = dotsListState,
            modifier = Modifier
                .padding(horizontal = dimens.mediumPadding),
            horizontalArrangement = Arrangement.spacedBy(dotSpacing.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = dimens.mediumPadding)
        ) {
            items(safeImages.size) { index ->
                Icon(
                    imageVector = Icons.Filled.Circle,
                    contentDescription = null,
                    modifier = Modifier.size(dotSize.dp),
                    tint = if (index == pagerState.currentPage) colors.primary  else Color.Gray
                )
            }
        }

        Spacer(Modifier.height(dimens.smallPadding))
    }
}


@Preview(showBackground = true)
@Composable
fun CarouselWithDotsPreview() {
    AppTheme {
        ImageCarouselWithIndicator(
            imageUrls = listOf(
                "https://picsum.photos/id/1011/400/400",
                "https://picsum.photos/id/1012/400/400",
                "https://picsum.photos/id/1013/400/400"
            )
        )
    }
}
