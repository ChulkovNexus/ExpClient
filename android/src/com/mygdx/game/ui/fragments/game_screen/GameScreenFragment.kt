package com.mygdx.game.ui.fragments.game_screen

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mygdx.game.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class GameScreenFragment : Fragment(), GameScreenPresenter.GameScreenView {

    @Inject
    lateinit var presenter: GameScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_screen, container, false)
    }
}