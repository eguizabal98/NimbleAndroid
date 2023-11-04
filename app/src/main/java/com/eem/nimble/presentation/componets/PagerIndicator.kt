package com.eem.nimble.presentation.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    currentPage: Int,
    pageCount: Int,
    indicatorCount: Int = 5,
    indicatorSize: Dp = 12.dp,
    indicatorShape: Shape = CircleShape,
    space: Dp = 8.dp,
    activeColor: Color = Color(0xFFFFFFFF),
    inActiveColor: Color = Color(0x33FFFFFF)
) {

    val listState = rememberLazyListState()

    val totalWidth: Dp = indicatorSize * indicatorCount + space * (indicatorCount - 1)
    val widthInPx = LocalDensity.current.run { indicatorSize.toPx() }

    LaunchedEffect(key1 = currentPage) {
        val viewportSize = listState.layoutInfo.viewportSize
        listState.animateScrollToItem(
            currentPage,
            (widthInPx / 2 - viewportSize.width / 2).toInt()
        )
    }


    LazyRow(
        modifier = modifier.width(totalWidth),
        state = listState,
        contentPadding = PaddingValues(vertical = space),
        horizontalArrangement = Arrangement.spacedBy(space),
        userScrollEnabled = false
    ) {

        items(pageCount) { index ->

            val isSelected = (index == currentPage)

            val centerItemIndex = indicatorCount / 2

            val right1 = (currentPage < centerItemIndex && index >= indicatorCount - 1)

            val right2 =
                (currentPage >= centerItemIndex &&
                        index >= currentPage + centerItemIndex &&
                        index <= pageCount - centerItemIndex + 1)
            val isRightEdgeItem = right1 || right2

            val isLeftEdgeItem =
                index <= currentPage - centerItemIndex &&
                        currentPage > centerItemIndex &&
                        index < pageCount - indicatorCount + 1

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        val scale = if (isSelected) {
                            1f
                        } else if (isLeftEdgeItem || isRightEdgeItem) {
                            .5f
                        } else {
                            .8f
                        }
                        scaleX = scale
                        scaleY = scale

                    }

                    .clip(indicatorShape)
                    .size(indicatorSize)
                    .background(
                        if (isSelected) activeColor else inActiveColor,
                        indicatorShape
                    )
            )
        }
    }
}
