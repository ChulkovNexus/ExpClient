package com.mygdx.game.ui

import com.mygdx.game.R
import com.mygdx.game.ui.fragments.auth.AuthFragment
import javax.inject.Inject


class MainRouter @Inject
constructor(var activity: MainActivity) {

    fun showSomeScreen() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, AuthFragment(), "tag")
        transaction.commit()
    }
}