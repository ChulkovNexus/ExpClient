package com.mygdx.game.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.mygdx.game.R
import com.mygdx.game.di.DataManager
import com.mygdx.game.net.SocketService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class MainActivity : BaseActivity() , AndroidFragmentApplication.Callbacks{

    @Inject
    lateinit var socketService: SocketService//Singleton scope

    @Inject
    lateinit var dataManager: DataManager//activity scope

    @Inject
    lateinit var router: MainRouter//activity scope

    private val subs = CompositeDisposable()
    private lateinit var connectionView: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        connectionView = findViewById(R.id.connection_progress)
        router.showSomeScreen()
    }

    override fun onStart() {
        super.onStart()
        val disposable = socketService.connectionStatus.observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (it == SocketService.ConnectionStatus.CONNECTED) {
                connectionView.visibility = View.GONE
                router.showCurrentScreen()
            } else {
                connectionView.visibility = View.VISIBLE
                router.hideCurrentScreen()
            }
        }
        subs.add(disposable)
        socketService.connect()
    }

    override fun onStop() {
        super.onStop()
        subs.dispose()
        subs.clear()
        socketService.disconnect()
    }

    override fun exit() { }
}