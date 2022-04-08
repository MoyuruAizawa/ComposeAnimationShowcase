package io.moyuru.composeanimationshowcase.data

import androidx.annotation.StringRes
import io.moyuru.composeanimationshowcase.R

enum class Page(@StringRes val title: Int? = null, val hasAppBar: Boolean = true) {
  TOP(title = R.string.app_name, hasAppBar = true),
  BOTTOM_SHEET(title = R.string.page_title_bottom_sheet),
}