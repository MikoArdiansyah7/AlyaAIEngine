package com.alya.aiengine.ui.video

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

/**
 * Plays a local raw-resource video fullscreen, always preserving its original
 * aspect ratio and CENTER-CROPPING to fill the screen instead of stretching it
 * (RESIZE_MODE_ZOOM). This matters most for `background_video`, which is a
 * vertical source video being displayed on a landscape screen.
 *
 * @param rawRes the res/raw video resource (intro_video, background_video, play_intro_video)
 * @param loop whether to loop playback (true for the home background only)
 * @param muted whether audio should be silenced (true for intro + background, false for play_intro)
 * @param onCompleted called once when a non-looping video finishes playing
 */
@OptIn(UnstableApi::class)
@Composable
fun FullScreenVideo(
    @RawRes rawRes: Int,
    loop: Boolean,
    muted: Boolean,
    modifier: Modifier = Modifier,
    onCompleted: () -> Unit = {}
) {
    val context = LocalContext.current

    val exoPlayer = remember(rawRes) {
        ExoPlayer.Builder(context).build().apply {
            val uri = "android.resource://${context.packageName}/$rawRes"
            setMediaItem(MediaItem.fromUri(uri))
            repeatMode = if (loop) Player.REPEAT_MODE_ONE else Player.REPEAT_MODE_OFF
            volume = if (muted) 0f else 1f
            playWhenReady = true
            prepare()
        }
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (!loop && playbackState == Player.STATE_ENDED) {
                    onCompleted()
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            // Always stop + release so background audio can never bleed into
            // the next video (e.g. background_video -> play_intro_video).
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM // keep aspect ratio, center-crop
                setShutterBackgroundColor(android.graphics.Color.BLACK)
            }
        }
    )
}
