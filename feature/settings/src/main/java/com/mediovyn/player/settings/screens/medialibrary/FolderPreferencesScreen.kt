package com.mediovyn.player.settings.screens.medialibrary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mediovyn.player.core.ui.R
import com.mediovyn.player.core.ui.base.DataState
import com.mediovyn.player.core.ui.components.MediovynTopAppBar
import com.mediovyn.player.core.ui.components.SelectablePreference
import com.mediovyn.player.core.ui.components.rememberTvListFocusRequester
import com.mediovyn.player.core.ui.components.tvFocusDown
import com.mediovyn.player.core.ui.components.tvListFocus
import com.mediovyn.player.core.ui.designsystem.MediovynIcons
import com.mediovyn.player.core.ui.extensions.plus
import com.mediovyn.player.core.ui.theme.MediovynTheme

@Composable
fun FolderPreferencesScreen(
    viewModel: FolderPreferencesViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle(minActiveState = Lifecycle.State.RESUMED)

    FolderPreferencesScreenContent(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun FolderPreferencesScreenContent(
    state: FolderPreferencesUiState,
    onAction: (FolderPreferencesUiEvent) -> Unit,
) {
    val listFocusRequester = rememberTvListFocusRequester()
    Scaffold(
        topBar = {
            MediovynTopAppBar(
                title = stringResource(id = R.string.manage_folders),
                navigationIcon = {
                    FilledTonalIconButton(
                        onClick = { onAction(FolderPreferencesUiEvent.NavigateUp) },
                        modifier = Modifier.tvFocusDown(listFocusRequester),
                    ) {
                        Icon(
                            imageVector = MediovynIcons.ArrowBack,
                            contentDescription = stringResource(id = R.string.navigate_up),
                        )
                    }
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) { innerPadding ->
        when (state.foldersDataState) {
            is DataState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }

            is DataState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .tvListFocus(listFocusRequester),
                    contentPadding = innerPadding + PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
                ) {
                    itemsIndexed(state.foldersDataState.value) { index, folder ->
                        SelectablePreference(
                            title = folder.name,
                            description = folder.path,
                            selected = folder.path in state.preferences.excludeFolders,
                            onClick = { onAction(FolderPreferencesUiEvent.UpdateExcludeList(folder.path)) },
                            isFirstItem = index == 0,
                            isLastItem = index == state.foldersDataState.value.lastIndex,
                        )
                    }
                }
            }

            is DataState.Error -> Unit
        }
    }
}

@PreviewLightDark
@Composable
private fun FolderPreferencesScreenPreview() {
    MediovynTheme {
        FolderPreferencesScreenContent(
            state = FolderPreferencesUiState(),
            onAction = {},
        )
    }
}
