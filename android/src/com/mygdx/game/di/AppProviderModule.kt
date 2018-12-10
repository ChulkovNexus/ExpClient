package com.mygdx.game.di

import com.mygdx.game.net.SocketService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppProviderModule {

    @Provides
    @Singleton
    fun provideDataManager(): DataManager = DataManager()

    @Provides
    @Singleton
    fun provideSocketService(): SocketService = SocketService()
}