package com.mediovyn.player.core.data

import com.mediovyn.player.core.data.repository.NetworkConnectionRepository
import com.mediovyn.player.core.database.DatabaseModule
import com.mediovyn.player.core.datastore.di.DataStoreModule
import com.mediovyn.player.core.media.MediaModule
import com.mediovyn.player.core.media.network.NetworkConnectionResolver
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [DatabaseModule::class, DataStoreModule::class, MediaModule::class])
@ComponentScan
class DataModule {
    /** Lets `core:media` resolve a playback uri's connection id without depending on `core:data`. */
    @Single
    fun providesNetworkConnectionResolver(
        repository: NetworkConnectionRepository,
    ): NetworkConnectionResolver = NetworkConnectionResolver { id -> repository.getConnection(id) }
}
