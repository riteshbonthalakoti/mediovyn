package com.mediovyn.player.core.data.licensing

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit

/**
 * 7-Day All-Access Trial & Pro License Manager for MEDIOVYN.
 * Backed by SharedPreferences persistence.
 */
class TrialManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("mediovyn_licensing", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch_timestamp"
        private const val KEY_PRO_UNLOCKED = "pro_unlocked"
        const val TRIAL_DURATION_DAYS = 7L
    }

    private val _isProUnlocked = MutableStateFlow(checkProState())
    val isProUnlocked: Flow<Boolean> = _isProUnlocked.asStateFlow()

    private val _trialDaysRemaining = MutableStateFlow(calculateRemainingDays())
    val trialDaysRemaining: Flow<Long> = _trialDaysRemaining.asStateFlow()

    init {
        initializeTrialIfNeeded()
    }

    fun initializeTrialIfNeeded() {
        if (!prefs.contains(KEY_FIRST_LAUNCH)) {
            prefs.edit().putLong(KEY_FIRST_LAUNCH, System.currentTimeMillis()).apply()
        }
        updateState()
    }

    private fun checkProState(): Boolean {
        if (prefs.getBoolean(KEY_PRO_UNLOCKED, false)) return true
        val firstLaunch = prefs.getLong(KEY_FIRST_LAUNCH, System.currentTimeMillis())
        val elapsedMs = System.currentTimeMillis() - firstLaunch
        val elapsedDays = TimeUnit.MILLISECONDS.toDays(elapsedMs)
        return elapsedDays < TRIAL_DURATION_DAYS
    }

    private fun calculateRemainingDays(): Long {
        if (prefs.getBoolean(KEY_PRO_UNLOCKED, false)) return TRIAL_DURATION_DAYS
        val firstLaunch = prefs.getLong(KEY_FIRST_LAUNCH, System.currentTimeMillis())
        val elapsedMs = System.currentTimeMillis() - firstLaunch
        val elapsedDays = TimeUnit.MILLISECONDS.toDays(elapsedMs)
        return (TRIAL_DURATION_DAYS - elapsedDays).coerceAtLeast(0L)
    }

    fun unlockProLicense() {
        prefs.edit().putBoolean(KEY_PRO_UNLOCKED, true).apply()
        updateState()
    }

    private fun updateState() {
        _isProUnlocked.value = checkProState()
        _trialDaysRemaining.value = calculateRemainingDays()
    }
}