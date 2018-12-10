package com.mygdx.game.ui.fragments.game_screen

import com.mygdx.game.di.DataManager
import com.mygdx.game.net.SocketService
import com.mygdx.game.ui.BasePresenter
import javax.inject.Inject

class GameScreenPresenter @Inject constructor(view: GameScreenView, socketService: SocketService, dataManager: DataManager): BasePresenter<GameScreenPresenter.GameScreenView>(view) {

    interface GameScreenView {
    }
}