package com.mygdx.game.di

import com.mygdx.game.ui.fragments.auth.AuthFragment
import com.mygdx.game.ui.fragments.auth.AuthPresenter
import dagger.Binds
import dagger.Module


@Module
interface FragmentsModule {

    @Binds
    fun myView(authFragment: AuthFragment): AuthPresenter.AuthView
}