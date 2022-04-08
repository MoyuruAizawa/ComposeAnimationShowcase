package io.moyuru.composeanimationshowcase.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val palette = darkColors(
  primary = Colors.white,
  secondary = Colors.white,
  primaryVariant = Colors.white,
  secondaryVariant = Colors.white,
  background = Colors.black,
  surface = Colors.black,
  error = Colors.red,
  onPrimary = Colors.black,
  onSecondary = Colors.black,
  onBackground = Colors.white,
  onSurface = Colors.white
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colors = palette,
    content = content
  )
}