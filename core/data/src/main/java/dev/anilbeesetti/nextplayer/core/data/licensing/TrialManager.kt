package dev.anilbeesetti.nextplayer.core.data.licensing

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

private val Context.licensingDataStore by preferencesDataStore(name = "mediovyn_licensing")

/**
 * 7-Day All-Access Trial & Pro License Manager for MEDIOVYN.
 */
class TrialManager(private val context: Context) {

    companion object {
        private val FIRST_LAUNCH_TIMESTAMP = longPreferencesKey("first_launch_timestamp")
        private val PRO_UNLOCKED = longPreferencesKey("pro_unlocked")
        const val TRIAL_DURATION_DAYS = 7L
    }

    suspend fun initializeTrialIfNeeded() {
        context.licensingDataStore.edit { prefs ->
            if (prefs[FIRST_LAUNCH_TIMESTAMP] == null) {
                prefs[FIRST_LAUNCH_TIMESTAMP] = System.currentTimeMillis()
            }
        }
    }

    fun getTrialDaysRemaining(): Flow<Long> {
        return context.licensingDataStore.data.map { prefs ->
            val firstLaunch = prefs[FIRST_LAUNCH_TIMESTAMP] ?: System.currentTimeMillis()
            val elapsedMs = System.currentTimeMillis() - firstLaunch
            val elapsedDays = TimeUnit.MILLISECONDS.toDays(elapsedMs)
            (TRIAL_DURATION_DAYS - elapsedDays).coerceAtLeast(0L)
        }
    }

    fun isProUnlocked(): Flow<Boolean> {
        return context.licensingDataStore.data.map { prefs ->
            val isPro = (prefs[PRO_UNLOCKED] ?: 0L) == 1L
            if (isPro) return@map true
            
            val firstLaunch = prefs[FIRST_LAUNCH_TIMESTAMP] ?: System.currentTimeMillis()
            val elapsedMs = System.currentTimeMillis() - firstLaunch
            val elapsedDays = TimeUnit.MILLISECONDS.toDays(elapsedMs)
            elapsedDays < TRIAL_DURATION_DAYS
        }
    }

    suspend fun unlockProLicense() {
        context.licensingDataStore.edit { prefs ->
            prefs[PRO_UNLOCKED] = 1L
        }
    }
}