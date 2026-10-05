package com.mediovyn.player.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.mediovyn.player.core.common.di.DiQualifiers
import com.mediovyn.player.core.common.di.DispatchersModule
import com.mediovyn.player.core.datastore.serializer.ApplicationPreferencesSerializer
import com.mediovyn.player.core.datastore.serializer.PlayerPreferencesSerializer
import com.mediovyn.player.core.datastore.serializer.SearchHistorySerializer
import com.mediovyn.player.core.model.ApplicationPreferences
import com.mediovyn.player.core.model.PlayerPreferences
import com.mediovyn.player.core.model.SearchHistory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private const val APP_PREFERENCES_DATASTORE_FILE = "app_preferences.json"
private const val PLAYER_PREFERENCES_DATASTORE_FILE = "player_preferences.json"
private const val SEARCH_HISTORY_DATASTORE_FILE = "search_history.json"

@Module(includes = [DispatchersModule::class])
@ComponentScan("com.mediovyn.player.core.datastore")
class DataStoreModule {

    @Single
    @Named(DiQualifiers.APP_PREFERENCES)
    fun provideAppPreferencesDataStore(
        context: Context,
        @Named(DiQualifiers.IO_DISPATCHER) ioDispatcher: CoroutineDispatcher,
        @Named(DiQualifiers.APPLICATION_SCOPE) scope: CoroutineScope,
    ): DataStore<ApplicationPreferences> = DataStoreFactory.create(
        serializer = ApplicationPreferencesSerializer,
        scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        produceFile = { context.dataStoreFile(APP_PREFERENCES_DATASTORE_FILE) },
    )

    @Single
    @Named(DiQualifiers.PLAYER_PREFERENCES)
    fun providePlayerPreferencesDataStore(
        applicationContext: Context,
        @Named(DiQualifiers.IO_DISPATCHER) ioDispatcher: CoroutineDispatcher,
        @Named(DiQualifiers.APPLICATION_SCOPE) scope: CoroutineScope,
    ): DataStore<PlayerPreferences> = DataStoreFactory.create(
        serializer = PlayerPreferencesSerializer,
        scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        produceFile = { applicationContext.dataStoreFile(PLAYER_PREFERENCES_DATASTORE_FILE) },
    )

    @Single
    @Named(DiQualifiers.SEARCH_HISTORY)
    fun provideSearchHistoryDataStore(
        applicationContext: Context,
        @Named(DiQualifiers.IO_DISPATCHER) ioDispatcher: CoroutineDispatcher,
        @Named(DiQualifiers.APPLICATION_SCOPE) scope: CoroutineScope,
    ): DataStore<SearchHistory> = DataStoreFactory.create(
        serializer = SearchHistorySerializer,
        scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        produceFile = { applicationContext.dataStoreFile(SEARCH_HISTORY_DATASTORE_FILE) },
    )
}
