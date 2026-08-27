package com.mediovyn.player.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.mediovyn.player.settings.Setting
import com.mediovyn.player.settings.navigation.aboutPreferencesEntry
import com.mediovyn.player.settings.navigation.appearancePreferencesEntry
import com.mediovyn.player.settings.navigation.audioPreferencesEntry
import com.mediovyn.player.settings.navigation.folderPreferencesEntry
import com.mediovyn.player.settings.navigation.generalPreferencesEntry
import com.mediovyn.player.settings.navigation.gesturePreferencesEntry
import com.mediovyn.player.settings.navigation.librariesEntry
import com.mediovyn.player.settings.navigation.mediaLibraryPreferencesEntry
import com.mediovyn.player.settings.navigation.navigateToAboutPreferences
import com.mediovyn.player.settings.navigation.navigateToAppearancePreferences
import com.mediovyn.player.settings.navigation.navigateToAudioPreferences
import com.mediovyn.player.settings.navigation.navigateToFolderPreferencesScreen
import com.mediovyn.player.settings.navigation.navigateToGeneralPreferences
import com.mediovyn.player.settings.navigation.navigateToGesturePreferences
import com.mediovyn.player.settings.navigation.navigateToLibraries
import com.mediovyn.player.settings.navigation.navigateToMediaLibraryPreferencesScreen
import com.mediovyn.player.settings.navigation.navigateToPlayerPreferences
import com.mediovyn.player.settings.navigation.navigateToSubtitlePreferences
import com.mediovyn.player.settings.navigation.navigateToThumbnailPreferencesScreen
import com.mediovyn.player.settings.navigation.playerPreferencesEntry
import com.mediovyn.player.settings.navigation.settingsEntry
import com.mediovyn.player.settings.navigation.subtitlePreferencesEntry
import com.mediovyn.player.settings.navigation.thumbnailPreferencesEntry

fun EntryProviderScope<NavKey>.settingsNavGraph(
    backStack: NavBackStack<NavKey>,
) {
    settingsEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
        onItemClick = { setting ->
            when (setting) {
                Setting.APPEARANCE -> backStack.navigateToAppearancePreferences()
                Setting.MEDIA_LIBRARY -> backStack.navigateToMediaLibraryPreferencesScreen()
                Setting.PLAYER -> backStack.navigateToPlayerPreferences()
                Setting.GESTURES -> backStack.navigateToGesturePreferences()
                Setting.AUDIO -> backStack.navigateToAudioPreferences()
                Setting.SUBTITLE -> backStack.navigateToSubtitlePreferences()
                Setting.GENERAL -> backStack.navigateToGeneralPreferences()
                Setting.ABOUT -> backStack.navigateToAboutPreferences()
            }
        },
    )
    appearancePreferencesEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
    )
    mediaLibraryPreferencesEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
        onFolderSettingClick = backStack::navigateToFolderPreferencesScreen,
        onThumbnailSettingClick = backStack::navigateToThumbnailPreferencesScreen,
    )
    thumbnailPreferencesEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
    )
    folderPreferencesEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
    )
    playerPreferencesEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
    )
    gesturePreferencesEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
    )
    audioPreferencesEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
    )
    subtitlePreferencesEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
    )
    generalPreferencesEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
    )
    aboutPreferencesEntry(
        onLibrariesClick = backStack::navigateToLibraries,
        onNavigateUp = { backStack.removeLastIfNotRoot() },
    )
    librariesEntry(
        onNavigateUp = { backStack.removeLastIfNotRoot() },
    )
}
