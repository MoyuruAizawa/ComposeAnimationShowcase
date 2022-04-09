package io.moyuru.composeanimationshowcase.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import io.moyuru.composeanimationshowcase.data.IMAGE_ASPECT_RATIO
import io.moyuru.composeanimationshowcase.data.catImages
import io.moyuru.composeanimationshowcase.ui.theme.Colors

@Preview
@Composable
fun ParallaxScrollScreen() {
  val scrollState = rememberScrollState()
  var columnHeight by remember { mutableStateOf(0) }
  Column(
    modifier = Modifier
      .onGloballyPositioned { columnHeight = it.size.height }
      .verticalScroll(scrollState)
      .fillMaxSize()
  ) {
    val borderWidth = 24.dp
    catImages.forEach {
      val painter = painterResource(id = it)
      var bottom by remember(key1 = painter) { mutableStateOf(0f) }
      Box(
        modifier = Modifier
          .onGloballyPositioned { bottom = it.positionInParent().y - scrollState.value + it.size.height }
          .aspectRatio(IMAGE_ASPECT_RATIO)
          .fillMaxWidth()
      ) {
        val density = LocalDensity.current
        Image(
          painter = painter,
          contentDescription = "CatImage",
          modifier = Modifier
            .graphicsLayer {
              val fraction = 1 - (bottom / columnHeight).coerceIn(0f, 1f)
              translationY = density.run { lerp(0.dp, borderWidth * 1.5f, fraction).toPx() }
            }
            .fillMaxSize()
        )
        Spacer(
          modifier = Modifier
            .fillMaxSize()
            .border(borderWidth, Colors.black)
        )
      }
    }
  }
}