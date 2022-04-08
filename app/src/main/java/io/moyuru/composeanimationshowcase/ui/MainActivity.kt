package io.moyuru.composeanimationshowcase.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.moyuru.composeanimationshowcase.AppNavigator
import io.moyuru.composeanimationshowcase.LocalNavigator
import io.moyuru.composeanimationshowcase.R
import io.moyuru.composeanimationshowcase.data.Page
import io.moyuru.composeanimationshowcase.ui.screen.BottomSheetScreen
import io.moyuru.composeanimationshowcase.ui.screen.TopScreen
import io.moyuru.composeanimationshowcase.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    setContent {
      AppTheme {
        ProvideWindowInsets {
          // A surface container using the 'background' color from the theme
          Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
          ) {
            Content()
          }
        }
      }
    }
  }

  @Composable
  private fun Content() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val systemUiController = rememberSystemUiController()
    SideEffect {
      systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = false
      )
    }

    CompositionLocalProvider(LocalNavigator provides AppNavigator(navController)) {
      val page = Page.values().firstOrNull { it.name == currentBackStack?.destination?.route }
      Scaffold(
        topBar = { if (page?.hasAppBar == true) TopAppBar(page, navController) },
        content = {
          NavHost(
            navController = navController,
            startDestination = Page.TOP.name
          ) {
            composable(Page.TOP.name) { TopScreen() }
            composable(Page.BOTTOM_SHEET.name) { BottomSheetScreen() }
          }
        }
      )
    }
  }

  @Composable
  private fun TopAppBar(
    page: Page,
    navController: NavHostController
  ) {
    val inset = LocalWindowInsets.current
    val density = LocalDensity.current
    io.moyuru.composeanimationshowcase.ui.composable.TopAppBar(
      navigationIcon = if (page != Page.TOP) {
        {
          IconButton(onClick = { navController.popBackStack() }) {
            Image(
              painter = painterResource(id = R.drawable.ic_arrow_back),
              contentDescription = null
            )
          }
        }
      } else {
        null
      },
      title = {
        if (page.title != null && page.hasAppBar)
          Text(text = stringResource(id = page.title))
      },
      contentPadding = PaddingValues(top = density.run { inset.statusBars.top.toDp() }),
      modifier = Modifier.fillMaxWidth()
    )
  }
}