package com.mediovyn.player.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mediovyn.player.feature.player.PlayerActivity
import com.mediovyn.player.feature.player.utils.PlaylistPlaybackContract
import com.mediovyn.player.feature.playlist.navigation.navigateToPlaylistDetail
import com.mediovyn.player.feature.playlist.navigation.playlistDetailEntry
import com.mediovyn.player.feature.playlist.navigation.playlistListEntry
import com.mediovyn.player.settings.navigation.navigateToSettings

fun EntryProviderScope<NavKey>.playlistNavGraph(
    context: Context,
    backStack: NavBackStack<NavKey>,
) {
    playlistListEntry(
        onPlaylistClick = backStack::navigateToPlaylistDetail,
        onSettingsClick = backStack::navigateToSettings,
    )

    playlistDetailEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
        onPlayPlaylist = { playlistId, startUri ->
            context.startPlaylistPlayback(
                playlistId = playlistId,
                startUri = startUri,
            )
        },
    )
}

internal fun Context.startPlaylistPlayback(
    playlistId: Long,
    startUri: Uri,
) {
    startActivity(
        Intent(this, PlayerActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            data = startUri
            putExtra(PlaylistPlaybackContract.EXTRA_PLAYLIST_ID, playlistId)
        },
    )
}
