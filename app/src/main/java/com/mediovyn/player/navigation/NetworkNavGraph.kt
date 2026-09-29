package com.mediovyn.player.navigation

import android.content.Context
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mediovyn.player.feature.network.navigation.addConnectionEntry
import com.mediovyn.player.feature.network.navigation.navigateToAddConnection
import com.mediovyn.player.feature.network.navigation.navigateToNetworkBrowse
import com.mediovyn.player.feature.network.navigation.networkBrowseEntry
import com.mediovyn.player.feature.network.navigation.networkEntry
import com.mediovyn.player.settings.navigation.navigateToSettings

fun EntryProviderScope<NavKey>.networkNavGraph(
    context: Context,
    backStack: NavBackStack<NavKey>,
) {
    networkEntry(
        onAddConnection = { backStack.navigateToAddConnection() },
        onEditConnection = { id -> backStack.navigateToAddConnection(id) },
        onOpenConnection = { id -> backStack.navigateToNetworkBrowse(id) },
        onSettingsClick = backStack::navigateToSettings,
        onOpenStream = { uri -> context.startPlayback(uri) },
    )

    addConnectionEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
    )

    networkBrowseEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
        onPlayVideos = { uris, startUri -> context.startPlayback(uris, startUri) },
        onNavigateToFolder = { id, path -> backStack.navigateToNetworkBrowse(id, path) },
    )
}
