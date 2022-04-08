package io.moyuru.composeanimationshowcase

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ColumnScope.Space(dp: Dp, modifier: Modifier = Modifier) {
  Spacer(modifier = Modifier.height(dp))
}

@Composable
fun RowScope.Space(dp: Dp, modifier: Modifier = Modifier) {
  Spacer(modifier = Modifier.width(dp))
}
