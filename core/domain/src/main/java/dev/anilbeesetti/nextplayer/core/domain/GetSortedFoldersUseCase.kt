package com.mediovyn.player.core.domain

import com.mediovyn.player.core.common.di.DiQualifiers
import com.mediovyn.player.core.data.repository.MediaRepository
import com.mediovyn.player.core.data.repository.PreferencesRepository
import com.mediovyn.player.core.model.Folder
import com.mediovyn.player.core.model.Sort
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory
class GetSortedFoldersUseCase(
    private val mediaRepository: MediaRepository,
    private val preferencesRepository: PreferencesRepository,
    @Named(DiQualifiers.DEFAULT_DISPATCHER) private val defaultDispatcher: CoroutineDispatcher,
) {

    operator fun invoke(folderPath: String? = null): Flow<List<Folder>> {
        return combine(
            mediaRepository.observeFolders(folderPath),
            preferencesRepository.applicationPreferences,
        ) { folders, preferences ->

            val nonExcludedDirectories = folders.filter {
                it.path !in preferences.excludeFolders
            }

            val sort = Sort(by = preferences.sortBy, order = preferences.sortOrder)
            nonExcludedDirectories.sortedWith(sort.folderComparator())
        }.flowOn(defaultDispatcher)
    }
}
