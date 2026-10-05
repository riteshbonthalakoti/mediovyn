package com.mediovyn.player

import androidx.lifecycle.viewModelScope
import com.mediovyn.player.core.data.repository.PreferencesRepository
import com.mediovyn.player.core.model.ApplicationPreferences
import com.mediovyn.player.core.ui.base.MviViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val preferencesRepository: PreferencesRepository,
) : MviViewModel<MainActivityUiState, Nothing>() {

    private val stateInternal = MutableStateFlow<MainActivityUiState>(MainActivityUiState.Loading)
    override val state: StateFlow<MainActivityUiState> = stateInternal.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesRepository.applicationPreferences.collect { preferences ->
                stateInternal.update { MainActivityUiState.Success(preferences) }
            }
        }
    }

    // Application preferences are observed only; this view model has no input actions.
    override fun onAction(action: Nothing) = Unit
}

sealed interface MainActivityUiState {
    object Loading : MainActivityUiState
    data class Success(val preferences: ApplicationPreferences) : MainActivityUiState
}
