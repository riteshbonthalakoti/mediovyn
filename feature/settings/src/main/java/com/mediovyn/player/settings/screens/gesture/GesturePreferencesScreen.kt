package com.mediovyn.player.settings.screens.gesture

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mediovyn.player.core.common.extensions.round
import com.mediovyn.player.core.common.extensions.toString
import com.mediovyn.player.core.model.DoubleTapGesture
import com.mediovyn.player.core.model.PlayerPreferences
import com.mediovyn.player.core.ui.R
import com.mediovyn.player.core.ui.components.ListSectionTitle
import com.mediovyn.player.core.ui.components.NextDialogWithDoneAndCancelButtons
import com.mediovyn.player.core.ui.components.MediovynTopAppBar
import com.mediovyn.player.core.ui.components.PreferenceSlider
import com.mediovyn.player.core.ui.components.PreferenceSwitch
import com.mediovyn.player.core.ui.components.PreferenceSwitchWithDivider
import com.mediovyn.player.core.ui.components.RadioTextButton
import com.mediovyn.player.core.ui.components.rememberTvListFocusRequester
import com.mediovyn.player.core.ui.components.tvFocusDown
import com.mediovyn.player.core.ui.components.tvListFocus
import com.mediovyn.player.core.ui.designsystem.MediovynIcons
import com.mediovyn.player.core.ui.preview.DayNightPreview
import com.mediovyn.player.core.ui.theme.MediovynTheme
import com.mediovyn.player.settings.composables.OptionsDialog
import com.mediovyn.player.settings.extensions.name

@Composable
fun GesturePreferencesScreen(
    viewModel: GesturePreferencesViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    GesturePreferencesScreenContent(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun GesturePreferencesScreenContent(
    state: GesturePreferencesUiState,
    onAction: (GesturePreferencesUiEvent) -> Unit,
) {
    val listFocusRequester = rememberTvListFocusRequester()
    Scaffold(
        topBar = {
            MediovynTopAppBar(
                title = stringResource(id = R.string.gestures),
                navigationIcon = {
                    FilledTonalIconButton(onClick = { onAction(GesturePreferencesUiEvent.NavigateUp) }, modifier = Modifier.tvFocusDown(listFocusRequester)) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .tvListFocus(listFocusRequester)
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            ListSectionTitle(text = stringResource(id = R.string.gestures))
            Column(
                verticalArrangement = Arrangement.spacedBy(ListItemDefaults.SegmentedGap),
            ) {
                PreferenceSwitch(
                    title = stringResource(id = R.string.seek_gesture),
                    description = stringResource(id = R.string.seek_gesture_description),
                    icon = MediovynIcons.SwipeHorizontal,
                    isChecked = state.preferences.useSeekControls,
                    onClick = { onAction(GesturePreferencesUiEvent.ToggleUseSeekControls) },
                    isFirstItem = true,
                )
                PreferenceSlider(
                    title = stringResource(R.string.seek_gesture_sensitivity),
                    description = state.preferences.seekSensitivity.toString(decimalPlaces = 2),
                    icon = MediovynIcons.Sensitivity,
                    enabled = state.preferences.useSeekControls,
                    value = state.preferences.seekSensitivity,
                    valueRange = 0.1f..2.0f,
                    onValueChange = { onAction(GesturePreferencesUiEvent.UpdateSeekSensitivity(it)) },
                    trailingContent = {
                        FilledIconButton(
                            enabled = state.preferences.useSeekControls,
                            onClick = { onAction(GesturePreferencesUiEvent.UpdateSeekSensitivity(PlayerPreferences.DEFAULT_SEEK_SENSITIVITY)) },
                        ) {
                            Icon(
                                imageVector = MediovynIcons.History,
                                contentDescription = stringResource(id = R.string.reset_seek_sensitivity),
                            )
                        }
                    },
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.brightness_gesture),
                    description = stringResource(id = R.string.brightness_gesture_description),
                    icon = MediovynIcons.SwipeVertical,
                    isChecked = state.preferences.enableBrightnessSwipeGesture,
                    onClick = { onAction(GesturePreferencesUiEvent.ToggleEnableBrightnessSwipeGesture) },
                )
                PreferenceSlider(
                    title = stringResource(R.string.brightness_gesture_sensitivity),
                    description = state.preferences.brightnessGestureSensitivity.toString(decimalPlaces = 2),
                    icon = MediovynIcons.Sensitivity,
                    enabled = state.preferences.enableBrightnessSwipeGesture,
                    value = state.preferences.brightnessGestureSensitivity,
                    valueRange = 0.1f..2.0f,
                    onValueChange = { onAction(GesturePreferencesUiEvent.UpdateBrightnessGestureSensitivity(it)) },
                    trailingContent = {
                        FilledIconButton(
                            enabled = state.preferences.enableBrightnessSwipeGesture,
                            onClick = { onAction(GesturePreferencesUiEvent.UpdateBrightnessGestureSensitivity(PlayerPreferences.DEFAULT_BRIGHTNESS_GESTURE_SENSITIVITY)) },
                        ) {
                            Icon(
                                imageVector = MediovynIcons.History,
                                contentDescription = stringResource(id = R.string.reset_brightness_gesture_sensitivity),
                            )
                        }
                    },
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.volume_gesture),
                    description = stringResource(id = R.string.volume_gesture_description),
                    icon = MediovynIcons.SwipeVertical,
                    isChecked = state.preferences.enableVolumeSwipeGesture,
                    onClick = { onAction(GesturePreferencesUiEvent.ToggleEnableVolumeSwipeGesture) },
                )
                PreferenceSlider(
                    title = stringResource(R.string.volume_gesture_sensitivity),
                    description = state.preferences.volumeGestureSensitivity.toString(decimalPlaces = 2),
                    icon = MediovynIcons.Sensitivity,
                    enabled = state.preferences.enableVolumeSwipeGesture,
                    value = state.preferences.volumeGestureSensitivity,
                    valueRange = 0.1f..2.0f,
                    onValueChange = { onAction(GesturePreferencesUiEvent.UpdateVolumeGestureSensitivity(it)) },
                    trailingContent = {
                        FilledIconButton(
                            enabled = state.preferences.enableVolumeSwipeGesture,
                            onClick = { onAction(GesturePreferencesUiEvent.UpdateVolumeGestureSensitivity(PlayerPreferences.DEFAULT_VOLUME_GESTURE_SENSITIVITY)) },
                        ) {
                            Icon(
                                imageVector = MediovynIcons.History,
                                contentDescription = stringResource(id = R.string.reset_volume_gesture_sensitivity),
                            )
                        }
                    },
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.zoom_gesture),
                    description = stringResource(id = R.string.zoom_gesture_description),
                    icon = MediovynIcons.Pinch,
                    isChecked = state.preferences.useZoomControls,
                    onClick = { onAction(GesturePreferencesUiEvent.ToggleUseZoomControls) },
                )
                PreferenceSwitch(
                    title = stringResource(id = R.string.pan_gesture),
                    description = stringResource(id = R.string.pan_gesture_description),
                    icon = MediovynIcons.Pan,
                    enabled = state.preferences.useZoomControls,
                    isChecked = state.preferences.enablePanGesture,
                    onClick = { onAction(GesturePreferencesUiEvent.ToggleEnablePanGesture) },
                )
                PreferenceSwitchWithDivider(
                    title = stringResource(id = R.string.double_tap),
                    description = stringResource(id = R.string.double_tap_description),
                    icon = MediovynIcons.DoubleTap,
                    isChecked = (state.preferences.doubleTapGesture != DoubleTapGesture.NONE),
                    onChecked = { onAction(GesturePreferencesUiEvent.ToggleDoubleTapGesture) },
                    onClick = { onAction(GesturePreferencesUiEvent.ShowDialog(GesturePreferenceDialog.DoubleTapDialog)) },
                )
                PreferenceSwitchWithDivider(
                    title = stringResource(id = R.string.long_press_gesture),
                    description = stringResource(id = R.string.long_press_gesture_desc, state.preferences.longPressControlsSpeed),
                    icon = MediovynIcons.Tap,
                    isChecked = state.preferences.useLongPressControls,
                    onChecked = { onAction(GesturePreferencesUiEvent.ToggleUseLongPressControls) },
                    onClick = { onAction(GesturePreferencesUiEvent.ShowDialog(GesturePreferenceDialog.LongPressControlsSpeedDialog)) },
                )
                PreferenceSlider(
                    title = stringResource(R.string.seek_increment),
                    description = stringResource(R.string.seconds, state.preferences.seekIncrement),
                    isLastItem = true,
                    icon = MediovynIcons.Replay,
                    value = state.preferences.seekIncrement.toFloat(),
                    valueRange = 1.0f..60.0f,
                    onValueChange = { onAction(GesturePreferencesUiEvent.UpdateSeekIncrement(it.toInt())) },
                    onReset = { onAction(GesturePreferencesUiEvent.UpdateSeekIncrement(PlayerPreferences.DEFAULT_SEEK_INCREMENT)) },
                    trailingContent = {
                        FilledIconButton(onClick = { onAction(GesturePreferencesUiEvent.UpdateSeekIncrement(PlayerPreferences.DEFAULT_SEEK_INCREMENT)) }) {
                            Icon(
                                imageVector = MediovynIcons.History,
                                contentDescription = stringResource(id = R.string.reset_seek_increment),
                            )
                        }
                    },
                )
            }
        }

        state.showDialog?.let { showDialog ->
            when (showDialog) {
                GesturePreferenceDialog.DoubleTapDialog -> {
                    OptionsDialog(
                        text = stringResource(id = R.string.double_tap),
                        onDismissClick = { onAction(GesturePreferencesUiEvent.ShowDialog(null)) },
                    ) {
                        items(DoubleTapGesture.entries.toTypedArray()) {
                            RadioTextButton(
                                text = it.name(),
                                selected = (it == state.preferences.doubleTapGesture),
                                onClick = {
                                    onAction(GesturePreferencesUiEvent.UpdateDoubleTapGesture(it))
                                    onAction(GesturePreferencesUiEvent.ShowDialog(null))
                                },
                            )
                        }
                    }
                }

                GesturePreferenceDialog.LongPressControlsSpeedDialog -> {
                    var longPressControlsSpeed by remember {
                        mutableFloatStateOf(state.preferences.longPressControlsSpeed)
                    }

                    NextDialogWithDoneAndCancelButtons(
                        title = stringResource(R.string.long_press_gesture),
                        onDoneClick = {
                            onAction(GesturePreferencesUiEvent.UpdateLongPressControlsSpeed(longPressControlsSpeed))
                            onAction(GesturePreferencesUiEvent.ShowDialog(null))
                        },
                        onDismissClick = { onAction(GesturePreferencesUiEvent.ShowDialog(null)) },
                        content = {
                            Text(
                                text = "$longPressControlsSpeed",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Slider(
                                value = longPressControlsSpeed,
                                onValueChange = { longPressControlsSpeed = it.round(1) },
                                valueRange = 0.2f..4.0f,
                            )
                        },
                    )
                }
            }
        }
    }
}

@DayNightPreview
@Composable
private fun GesturePreferencesScreenPreview() {
    MediovynTheme {
        GesturePreferencesScreenContent(
            state = GesturePreferencesUiState(),
            onAction = {},
        )
    }
}
