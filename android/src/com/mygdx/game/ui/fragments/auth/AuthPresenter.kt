package com.mygdx.game.ui.fragments.auth

import com.mygdx.game.di.DataManager
import com.mygdx.game.net.BaseMessage
import com.mygdx.game.net.CallbackedMessage
import com.mygdx.game.net.SocketService
import com.mygdx.game.net.messages.requests.AuthMessage
import com.mygdx.game.net.messages.responses.AuthResponse
import com.mygdx.game.ui.BasePresenter
import javax.inject.Inject

class AuthPresenter @Inject constructor(var authView: AuthView, var socketManager: SocketService, var dataManager: DataManager) : BasePresenter<AuthPresenter.AuthView>(authView) {

    fun login(login: String, password: String) {
        val message = AuthMessage.createCallbackedMessage(login, password)
        socketManager.sendMessage(message)
    }

    interface AuthView {
    }

}