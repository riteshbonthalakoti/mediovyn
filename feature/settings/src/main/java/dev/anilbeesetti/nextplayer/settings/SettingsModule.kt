package com.mediovyn.player.settings

import com.mediovyn.player.core.domain.DomainModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DomainModule::class])
@ComponentScan
class SettingsModule
