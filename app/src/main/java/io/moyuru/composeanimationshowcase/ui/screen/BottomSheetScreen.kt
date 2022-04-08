package io.moyuru.composeanimationshowcase.ui.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import io.moyuru.composeanimationshowcase.R
import io.moyuru.composeanimationshowcase.data.Song
import io.moyuru.composeanimationshowcase.data.songs
import io.moyuru.composeanimationshowcase.ui.composable.Space
import io.moyuru.composeanimationshowcase.ui.theme.Colors
import io.moyuru.composeanimationshowcase.ui.theme.TextStyles
import kotlin.random.Random

@Composable
fun BottomSheetScreen() {
  val density = LocalDensity.current
  var playingSong by remember { mutableStateOf(songs[Random(System.currentTimeMillis()).nextInt(songs.size)]) }
  var playingNext by remember { mutableStateOf(songs) }
  val scaffoldState = rememberBottomSheetScaffoldState()
  var peekHeight by remember { mutableStateOf(0) }

  val onClickSong = { song: Song ->
    playingSong = song
    val index = songs.indexOf(song)
    playingNext = if (index <= 0) songs.subList(index + 1, songs.lastIndex)
    else songs.subList(index + 1, songs.lastIndex) + songs.subList(0, index - 1)
  }
  val onClickNext = { onClickSong(playingNext.first()) }

  BottomSheetScaffold(
    scaffoldState = scaffoldState,
    sheetPeekHeight = density.run { peekHeight.toDp() },
    content = {
      Content(
        onClickSong = onClickSong,
        sheetPeekHeight = peekHeight,
        sheetOffset = scaffoldState.bottomSheetState.offset,
        modifier = Modifier
          .fillMaxSize()
      )
    },
    sheetContent = {
      val inset = LocalWindowInsets.current
      SheetContent(
        song = playingSong,
        playingNext = playingNext,
        onClickSong = onClickSong,
        onClickNext = onClickNext,
        onPlayerPositioned = { peekHeight = it + inset.navigationBars.bottom }
      )
    },
    modifier = Modifier.fillMaxSize()
  )
}

@Composable
private fun Content(
  onClickSong: (Song) -> Unit,
  sheetPeekHeight: Int,
  sheetOffset: State<Float>,
  modifier: Modifier = Modifier
) {
  var contentHeight by remember { mutableStateOf(0) }

  Column(
    modifier = modifier
      .onGloballyPositioned { contentHeight = it.size.height }
      .graphicsLayer {
        val visibleContentMaxHeight = contentHeight - sheetPeekHeight
        val coefficient = 1 - sheetOffset.value / visibleContentMaxHeight
        val scale = (1 - coefficient * 0.05f)
        scaleX = scale
        scaleY = scale
      }
      .verticalScroll(rememberScrollState())
      .padding(vertical = 4.dp)
  ) {
    songs.forEach { Song(it, onClick = { onClickSong(it) }) }
    Space(dp = LocalDensity.current.run { sheetPeekHeight.toDp() })
  }
}

@Composable
private fun SheetContent(
  song: Song,
  playingNext: List<Song>,
  onClickNext: () -> Unit,
  onClickSong: (Song) -> Unit,
  onPlayerPositioned: (height: Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier
      .verticalScroll(rememberScrollState())
      .navigationBarsPadding()
  ) {
    Player(
      song = song,
      onClickNext = onClickNext,
      onPlayerPositioned = onPlayerPositioned,
    )
    Space(16.dp)
    Text(text = "Playing Next", modifier = Modifier.padding(horizontal = 16.dp))
    Space(16.dp)
    playingNext.forEach { song ->
      Song(song = song, onClick = { onClickSong(song) })
    }
  }
}

@Composable
private fun Player(
  song: Song,
  onClickNext: () -> Unit,
  onPlayerPositioned: (height: Int) -> Unit,
  modifier: Modifier = Modifier
) {
  var isPlaying by remember { mutableStateOf(true) }
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .onGloballyPositioned { onPlayerPositioned(it.size.height) }
      .padding(horizontal = 16.dp, vertical = 16.dp)
  ) {
    Artwork()
    Space(dp = 16.dp)
    Text(
      text = song.name,
      style = TextStyles.text16,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      modifier = modifier.weight(1f)
    )
    Space(dp = 16.dp)
    Crossfade(targetState = isPlaying) { state ->
      IconButton(onClick = { isPlaying = !state }) {
        if (state)
          Image(
            painter = painterResource(id = R.drawable.ic_play),
            contentDescription = "Play"
          )
        else
          Image(
            painter = painterResource(id = R.drawable.ic_pause),
            contentDescription = "Pause"
          )
      }
    }
    IconButton(onClick = onClickNext) {
      Image(
        painter = painterResource(id = R.drawable.ic_fast_forward),
        contentDescription = "Fast Forward"
      )
    }
  }
}

@Composable
private fun Song(song: Song, onClick: () -> Unit, modifier: Modifier = Modifier) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .padding(horizontal = 16.dp, vertical = 4.dp)
  ) {
    Artwork()
    Column {
      Text(text = song.name, style = TextStyles.text14, maxLines = 1, overflow = TextOverflow.Ellipsis)
      Text(
        text = song.artist,
        style = TextStyles.text12,
        color = Colors.lightGray,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

@Composable
private fun Artwork(modifier: Modifier = Modifier) {
  Image(
    painter = painterResource(id = R.drawable.ic_music_note),
    contentDescription = "Artwork",
    modifier = modifier
      .size(48.dp)
      .background(Colors.darkGray, RoundedCornerShape(4.dp))
      .padding(8.dp)
  )
}
