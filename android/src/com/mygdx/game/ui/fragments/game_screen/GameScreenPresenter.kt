package com.mygdx.game.ui.fragments.game_screen

import com.mygdx.game.di.DataManager
import com.mygdx.game.models.MapTile
import com.mygdx.game.net.SocketService
import com.mygdx.game.net.messages.requests.GetMap
import com.mygdx.game.ui.BasePresenter
import com.mygdx.game.utils.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class GameScreenPresenter @Inject constructor(var view: GameScreenView, var socketService: SocketService, var dataManager: DataManager): BasePresenter<GameScreenPresenter.GameScreenView>(view) {

    fun loadMap() {
        val callbackedMessage = GetMap.createCallbackedMessage(dataManager.token)
        subscribers.add(callbackedMessage.response.observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.updateMap(it.world)
        })
        socketService.sendMessage(callbackedMessage)
    }

    interface GameScreenView {
        fun updateMap(map: ArrayList<ArrayList<MapTile>>)
    }
}