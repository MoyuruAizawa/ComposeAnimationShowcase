package io.moyuru.composeanimationshowcase.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import io.moyuru.composeanimationshowcase.data.IMAGE_ASPECT_RATIO
import io.moyuru.composeanimationshowcase.data.catImages
import kotlin.math.absoluteValue

@Preview
@Composable
fun PagerScrollScreen() {
  HorizontalPager(
    count = catImages.size,
    contentPadding = PaddingValues(horizontal = 48.dp),
    modifier = Modifier.padding(top = 16.dp)
  ) { page ->
    val image = painterResource(id = catImages.getOrNull(page) ?: return@HorizontalPager)
    Image(
      painter = image,
      contentDescription = "CatImage $page",
      modifier = Modifier
        .graphicsLayer {
          val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
          lerp(
            start = ScaleFactor(0.85f, 0.85f),
            stop = ScaleFactor(1f, 1f),
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
          ).also {
            scaleX = it.scaleX
            scaleY = it.scaleY
          }
        }
        .aspectRatio(IMAGE_ASPECT_RATIO)
        .fillMaxWidth()
    )
  }
}