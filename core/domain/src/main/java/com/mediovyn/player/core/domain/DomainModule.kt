package com.mediovyn.player.core.domain

import com.mediovyn.player.core.data.DataModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [DataModule::class])
@ComponentScan
class DomainModule
