package com.mygdx.game.ui.fragments.auth

import com.mygdx.game.di.DataManager
import com.mygdx.game.net.BaseMessage
import com.mygdx.game.net.CallbackedMessage
import com.mygdx.game.net.SocketService
import com.mygdx.game.net.messages.requests.AuthMessage
import com.mygdx.game.net.messages.responses.AuthResponse
import com.mygdx.game.ui.BasePresenter
import com.mygdx.game.ui.MainRouter
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class AuthPresenter @Inject constructor(var authView: AuthView, var socketManager: SocketService, var dataManager: DataManager, var router: MainRouter)
    : BasePresenter<AuthPresenter.AuthView>(authView) {

    fun login(login: String, password: String) {
        val message = AuthMessage.createCallbackedMessage(login, password)
        authView.showProgress(true)
        subscribers.add(message.response.observeOn(AndroidSchedulers.mainThread()).subscribe {
            dataManager.token = it.token
            router.showGameScreen()
        })
        socketManager.sendMessage(message)
    }

    override fun onStart() {
        super.onStart()
        authView.showProgress(false)
    }


    interface AuthView {
        fun showProgress(boolean: Boolean)
    }

}