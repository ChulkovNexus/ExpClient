package com.mygdx.game.di

import com.mygdx.game.ui.fragments.auth.AuthFragment
import com.mygdx.game.ui.fragments.auth.AuthPresenter
import com.mygdx.game.ui.fragments.game_screen.GameScreenFragment
import com.mygdx.game.ui.fragments.game_screen.GameScreenPresenter
import dagger.Binds
import dagger.Module


@Module
interface FragmentsModule {

    @Binds
    fun authView(authFragment: AuthFragment): AuthPresenter.AuthView

    @Binds
    fun gameScreenView(authFragment: GameScreenFragment): GameScreenPresenter.GameScreenView
}