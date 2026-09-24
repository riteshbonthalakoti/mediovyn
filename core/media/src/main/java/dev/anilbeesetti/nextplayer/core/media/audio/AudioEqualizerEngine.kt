package com.mediovyn.player.core.media.audio

import android.media.audiofx.Equalizer
import android.media.audiofx.LoudnessEnhancer
import android.util.Log

/**
 * Native Audio Equalizer & Volume Booster Engine for MEDIOVYN.
 * Wraps Android's AudioEffect Equalizer and LoudnessEnhancer APIs.
 */
class AudioEqualizerEngine {

    private var equalizer: Equalizer? = null
    private var loudnessEnhancer: LoudnessEnhancer? = null
    private var currentAudioSessionId: Int = 0

    var isEnabled: Boolean = false
        private set

    var volumeBoostDb: Int = 0
        private set

    fun attachToAudioSession(audioSessionId: Int) {
        if (audioSessionId == 0 || audioSessionId == currentAudioSessionId) return
        release()
        currentAudioSessionId = audioSessionId

        try {
            equalizer = Equalizer(0, audioSessionId).apply {
                enabled = isEnabled
            }
            loudnessEnhancer = LoudnessEnhancer(audioSessionId).apply {
                enabled = volumeBoostDb > 0
                setTargetGain(volumeBoostDb * 100)
            }
            Log.d("AudioEqualizerEngine", "Attached to audio session: $audioSessionId")
        } catch (e: Exception) {
            Log.e("AudioEqualizerEngine", "Failed to attach AudioEffect", e)
        }
    }

    fun setEqualizerEnabled(enabled: Boolean) {
        isEnabled = enabled
        try {
            equalizer?.enabled = enabled
        } catch (e: Exception) {
            Log.e("AudioEqualizerEngine", "Failed to toggle equalizer state", e)
        }
    }

    fun setBandGain(band: Short, gainMb: Short) {
        try {
            equalizer?.setBandLevel(band, gainMb)
        } catch (e: Exception) {
            Log.e("AudioEqualizerEngine", "Failed to set band level for band $band", e)
        }
    }

    fun getNumberOfBands(): Short = equalizer?.numberOfBands ?: 10

    fun getBandLevelRange(): ShortArray = equalizer?.bandLevelRange ?: shortArrayOf(-1500, 1500)

    fun getCenterFreq(band: Short): Int = equalizer?.getCenterFreq(band) ?: 0

    fun setVolumeBoost(gainDb: Int) {
        volumeBoostDb = gainDb.coerceIn(0, 15)
        try {
            loudnessEnhancer?.let { enhancer ->
                enhancer.setTargetGain(volumeBoostDb * 100)
                enhancer.enabled = volumeBoostDb > 0
            }
        } catch (e: Exception) {
            Log.e("AudioEqualizerEngine", "Failed to set volume boost gain", e)
        }
    }

    fun release() {
        try {
            equalizer?.release()
            loudnessEnhancer?.release()
        } catch (e: Exception) {
            Log.e("AudioEqualizerEngine", "Error releasing AudioEffect resources", e)
        } finally {
            equalizer = null
            loudnessEnhancer = null
            currentAudioSessionId = 0
        }
    }
}