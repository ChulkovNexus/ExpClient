package com.mygdx.game.di

import com.mygdx.game.ui.fragments.auth.AuthFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [FragmentsModule::class])
    fun myFragment(): AuthFragment
}