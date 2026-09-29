package com.mediovyn.player.core.datastore.datasource

import androidx.datastore.core.DataStore
import com.mediovyn.player.core.common.Logger
import com.mediovyn.player.core.common.di.DiQualifiers
import com.mediovyn.player.core.model.SearchHistory
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

@Factory(binds = [])
class SearchHistoryDataSource(
    @Named(DiQualifiers.SEARCH_HISTORY) private val searchHistoryDataStore: DataStore<SearchHistory>,
) {

    companion object {
        private const val TAG = "SearchHistoryDataSource"
    }

    val searchHistory: Flow<SearchHistory> = searchHistoryDataStore.data

    suspend fun update(
        transform: suspend (SearchHistory) -> SearchHistory,
    ) {
        try {
            searchHistoryDataStore.updateData(transform)
        } catch (ioException: Exception) {
            Logger.logError(TAG, "Failed to update search history: $ioException")
        }
    }
}
