package com.mygdx.game.di

import com.mygdx.game.ui.fragments.auth.AuthFragment
import com.mygdx.game.ui.fragments.game_screen.GameScreenFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    fun authFragment(): AuthFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    fun gameFragment(): GameScreenFragment
}