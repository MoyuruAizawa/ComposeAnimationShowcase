package io.moyuru.composeanimationshowcase.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.moyuru.composeanimationshowcase.LocalNavigator
import io.moyuru.composeanimationshowcase.Page

@Composable
fun TopScreen() {
  val nav = LocalNavigator.current
  Column(modifier = Modifier.fillMaxSize()) {
    Page.values().filter { it != Page.TOP }
      .forEach { page ->
        page.title?.let { Menu(text = stringResource(id = it), onClick = { nav.navigate(page) }) }
      }
  }
}

@Composable
fun Menu(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .padding(16.dp)
  ) {
    Text(text = text)
  }
}