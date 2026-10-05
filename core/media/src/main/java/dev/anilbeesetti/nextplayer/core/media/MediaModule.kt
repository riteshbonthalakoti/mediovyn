package com.mediovyn.player.core.media

import com.mediovyn.player.core.common.di.DispatchersModule
import com.mediovyn.player.core.database.DatabaseModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DatabaseModule::class, DispatchersModule::class])
@ComponentScan
class MediaModule
