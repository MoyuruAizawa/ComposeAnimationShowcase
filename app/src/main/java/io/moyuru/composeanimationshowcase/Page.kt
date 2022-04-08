package io.moyuru.composeanimationshowcase

import androidx.annotation.StringRes

enum class Page(@StringRes val title: Int? = null, val hasAppBar: Boolean = true) {
  TOP(title = R.string.app_name, hasAppBar = true),
  BOTTOM_SHEET(title = R.string.page_title_bottom_sheet),
}