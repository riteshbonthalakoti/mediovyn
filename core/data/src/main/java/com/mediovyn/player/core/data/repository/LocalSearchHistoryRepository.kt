package com.mediovyn.player.core.data.repository

import com.mediovyn.player.core.datastore.datasource.SearchHistoryDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class LocalSearchHistoryRepository(
    private val searchHistoryDataSource: SearchHistoryDataSource,
) : SearchHistoryRepository {

    override val searchHistory: Flow<List<String>> =
        searchHistoryDataSource.searchHistory.map { it.queries }

    override suspend fun addSearchQuery(query: String) {
        searchHistoryDataSource.update { history ->
            history.addQuery(query)
        }
    }

    override suspend fun removeSearchQuery(query: String) {
        searchHistoryDataSource.update { history ->
            history.removeQuery(query)
        }
    }

    override suspend fun clearHistory() {
        searchHistoryDataSource.update { history ->
            history.clear()
        }
    }
}
