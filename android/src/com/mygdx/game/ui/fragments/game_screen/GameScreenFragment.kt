package com.mygdx.game.ui.fragments.game_screen

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import com.mygdx.game.models.MapTile
import com.mygdx.game.ui.game.MyGdxGame
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class GameScreenFragment : AndroidFragmentApplication(), GameScreenPresenter.GameScreenView {


    @Inject
    lateinit var presenter: GameScreenPresenter
    lateinit var game: MyGdxGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        game = MyGdxGame()
        return initializeForView(game)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadMap()
    }

    override fun updateMap(map: ArrayList<ArrayList<MapTile>>) {
        game.setNewWorldMap(map)
    }
}
