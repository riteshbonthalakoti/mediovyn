package com.mediovyn.player

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.mediovyn.player.core.common.Logger
import com.mediovyn.player.core.common.di.DiQualifiers
import com.mediovyn.player.core.data.repository.NetworkConnectionRepository
import com.mediovyn.player.core.media.network.keys.SshKeyStore
import com.mediovyn.player.crash.CrashActivity
import com.mediovyn.player.crash.GlobalExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinApplication
import org.koin.core.qualifier.named
import org.koin.plugin.module.dsl.startKoin

@KoinApplication(modules = [AppModule::class])
class MediovynApplication :
    Application(),
    SingletonImageLoader.Factory {

    private val imageLoader: ImageLoader by inject()

    private val networkConnectionRepository: NetworkConnectionRepository by inject()

    private val sshKeyStore: SshKeyStore by inject()

    private val applicationScope: CoroutineScope by inject(named(DiQualifiers.APPLICATION_SCOPE))

    override fun onCreate() {
        super.onCreate()
        startKoin<MediovynApplication> {
            allowOverride(false)
            androidContext(this@MediovynApplication)
        }
        Thread.setDefaultUncaughtExceptionHandler(GlobalExceptionHandler(applicationContext, CrashActivity::class.java))
        applicationScope.launch {
            runCatching {
                initializeSshKeyStore(networkConnectionRepository, sshKeyStore)
            }.onFailure { error ->
                Logger.logError(TAG, "Couldn't reconcile SSH keys: ${error.message}")
            }
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader = imageLoader

    private companion object {
        const val TAG = "MediovynApplication"
    }
}
