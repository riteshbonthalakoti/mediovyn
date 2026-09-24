package com.mediovyn.player.feature.player.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mediovyn.player.core.media.audio.AudioEqualizerEngine

/**
 * 10-Band Audio Equalizer & Volume Booster Dialog for MEDIOVYN.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EqualizerDialog(
    equalizerEngine: AudioEqualizerEngine,
    onDismissRequest: () -> Unit,
) {
    var isEnabled by remember { mutableStateOf(equalizerEngine.isEnabled) }
    var volumeBoost by remember { mutableStateOf(equalizerEngine.volumeBoostDb.toFloat()) }
    val presets = listOf("Flat", "Bass Boost", "Vocal", "Rock", "Pop", "Jazz")
    var selectedPreset by remember { mutableStateOf("Flat") }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = Color(0xFF0A0A10).copy(alpha = 0.95f),
        contentColor = Color.White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "10-Band Audio Equalizer",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF7C3AED)
                )
                Switch(
                    checked = isEnabled,
                    onCheckedChange = {
                        isEnabled = it
                        equalizerEngine.setEqualizerEnabled(it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF7C3AED)
                    )
                )
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Volume Boost (+15dB)", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    Text("+${volumeBoost.toInt()} dB", style = MaterialTheme.typography.bodySmall, color = Color(0xFF06B6D4))
                }
                Slider(
                    value = volumeBoost,
                    onValueChange = {
                        volumeBoost = it
                        equalizerEngine.setVolumeBoost(it.toInt())
                    },
                    valueRange = 0f..15f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF06B6D4),
                        activeTrackColor = Color(0xFF06B6D4)
                    )
                )
            }

            Text("Presets", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(presets) { preset ->
                    FilterChip(
                        selected = selectedPreset == preset,
                        onClick = { selectedPreset = preset },
                        label = { Text(preset, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF7C3AED),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}