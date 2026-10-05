package com.mediovyn.player.settings.screens.medialibrary

import androidx.lifecycle.viewModelScope
import com.mediovyn.player.core.data.repository.MediaRepository
import com.mediovyn.player.core.data.repository.PreferencesRepository
import com.mediovyn.player.core.model.ApplicationPreferences
import com.mediovyn.player.core.model.Folder
import com.mediovyn.player.core.ui.base.DataState
import com.mediovyn.player.core.ui.base.MviViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.KoinViewModel

@KoinViewModel
class FolderPreferencesViewModel(
    mediaRepository: MediaRepository,
    private val preferencesRepository: PreferencesRepository,
    @InjectedParam internal var output: Output,
) : MviViewModel<FolderPreferencesUiState, FolderPreferencesUiEvent>() {

    data class Output(
        val navigateUp: () -> Unit,
    )

    private val stateInternal = MutableStateFlow(
        FolderPreferencesUiState(
            preferences = preferencesRepository.applicationPreferences.value,
        ),
    )
    override val state: StateFlow<FolderPreferencesUiState> = stateInternal.asStateFlow()

    init {
        viewModelScope.launch {
            mediaRepository.observeFolders().collect {
                stateInternal.update { currentState ->
                    currentState.copy(foldersDataState = DataState.Success(it))
                }
            }
        }

        viewModelScope.launch {
            preferencesRepository.applicationPreferences.collect { preferences ->
                stateInternal.update { currentState ->
                    currentState.copy(preferences = preferences)
                }
            }
        }
    }

    override fun onAction(action: FolderPreferencesUiEvent) {
        when (action) {
            is FolderPreferencesUiEvent.NavigateUp -> output.navigateUp()

            is FolderPreferencesUiEvent.UpdateExcludeList -> updateExcludeList(action.path)
        }
    }

    private fun updateExcludeList(path: String) {
        viewModelScope.launch {
            preferencesRepository.updateApplicationPreferences {
                it.copy(
                    excludeFolders = if (path in it.excludeFolders) {
                        it.excludeFolders - path
                    } else {
                        it.excludeFolders + path
                    },
                )
            }
        }
    }
}

data class FolderPreferencesUiState(
    val foldersDataState: DataState<List<Folder>> = DataState.Loading,
    val preferences: ApplicationPreferences = ApplicationPreferences(),
)

sealed interface FolderPreferencesUiEvent {
    data object NavigateUp : FolderPreferencesUiEvent

    data class UpdateExcludeList(val path: String) : FolderPreferencesUiEvent
}
