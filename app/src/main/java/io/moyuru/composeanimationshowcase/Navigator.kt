package io.moyuru.composeanimationshowcase

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import io.moyuru.composeanimationshowcase.data.Page

val LocalNavigator = staticCompositionLocalOf<Navigator> {
  error("navigator not found")
}

interface Navigator {
  fun navigateBack()
  fun navigate(page: Page)
}

class AppNavigator(private val nav: NavController) : Navigator {
  override fun navigateBack() {
    nav.popBackStack()
  }

  override fun navigate(page: Page) {
    nav.navigate(page.name)
  }
}
