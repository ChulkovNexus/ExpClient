package com.mygdx.game.ui

import android.os.Bundle
import com.mygdx.game.R
import com.mygdx.game.di.DataManager
import com.mygdx.game.net.SocketService
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var socketService: SocketService//Singleton scope

    @Inject
    lateinit var dataManager: DataManager//activity scope

    @Inject
    lateinit var router: MainRouter//activity scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router?.showSomeScreen()
    }

    override fun onStart() {
        super.onStart()
        socketService.connect()
    }

    override fun onPause() {
        super.onPause()
        socketService.disconnect()
    }
}