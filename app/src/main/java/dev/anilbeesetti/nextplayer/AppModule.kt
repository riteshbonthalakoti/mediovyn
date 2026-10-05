package com.mediovyn.player

import android.content.Context
import coil3.ImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.request.CachePolicy
import coil3.request.crossfade
import com.mediovyn.player.core.common.di.DispatchersModule
import com.mediovyn.player.core.data.DataModule
import com.mediovyn.player.core.data.repository.PreferencesRepository
import com.mediovyn.player.core.model.ThumbnailGenerationStrategy
import com.mediovyn.player.feature.network.NetworkModule
import com.mediovyn.player.feature.player.PlayerModule
import com.mediovyn.player.feature.playlist.PlaylistModule
import com.mediovyn.player.feature.videopicker.VideoPickerModule
import com.mediovyn.player.settings.SettingsModule
import okio.FileSystem
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.KoinViewModel
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [DispatchersModule::class, DataModule::class, NetworkModule::class, PlaylistModule::class, PlayerModule::class, SettingsModule::class, VideoPickerModule::class])
@ComponentScan("com.mediovyn.player.feature.more")
class AppModule {

    @KoinViewModel
    fun provideMainViewModel(preferencesRepository: PreferencesRepository) = MainViewModel(preferencesRepository)

    @Single
    fun provideImageLoader(
        context: Context,
        preferencesRepository: PreferencesRepository,
    ): ImageLoader = ImageLoader.Builder(context)
        .components {
            add(
                VideoThumbnailDecoder.Factory(
                    thumbnailStrategy = {
                        val preferences = preferencesRepository.applicationPreferences.value
                        when (preferences.thumbnailGenerationStrategy) {
                            ThumbnailGenerationStrategy.FIRST_FRAME -> ThumbnailStrategy.FirstFrame
                            ThumbnailGenerationStrategy.FRAME_AT_PERCENTAGE -> ThumbnailStrategy.FrameAtPercentage(preferences.thumbnailFramePosition)
                            ThumbnailGenerationStrategy.HYBRID -> ThumbnailStrategy.Hybrid(preferences.thumbnailFramePosition)
                        }
                    },
                ),
            )
        }
        .diskCachePolicy(CachePolicy.ENABLED)
        .diskCache(
            DiskCache.Builder()
                .fileSystem(FileSystem.SYSTEM)
                .directory(context.filesDir.resolve("thumbnails"))
                .maxSizePercent(1.0)
                .build(),
        )
        .crossfade(true)
        .build()
}
