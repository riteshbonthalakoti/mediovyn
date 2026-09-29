package com.mediovyn.player.core.domain

import com.mediovyn.player.core.common.di.DiQualifiers
import com.mediovyn.player.core.data.repository.VaultRepository
import com.mediovyn.player.core.model.Sort
import com.mediovyn.player.core.model.Video
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

/**
 * Returns videos currently hidden in the vault, sorted by the given [Sort] criteria.
 */
@Factory
class GetHiddenVideosUseCase(
    private val vaultRepository: VaultRepository,
    @Named(DiQualifiers.DEFAULT_DISPATCHER) private val defaultDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(sort: Sort): Flow<List<Video>> {
        return vaultRepository.observeHiddenVideos()
            .map { videos -> videos.sortedWith(sort.videoComparator()) }
            .flowOn(defaultDispatcher)
    }
}
