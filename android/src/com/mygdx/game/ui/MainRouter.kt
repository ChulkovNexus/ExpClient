package com.mygdx.game.ui

import com.mygdx.game.R
import com.mygdx.game.ui.fragments.auth.AuthFragment
import com.mygdx.game.ui.fragments.game_screen.GameScreenFragment
import javax.inject.Inject


class MainRouter @Inject
constructor(var activity: MainActivity) {

    companion object {
        const val MAIN_TAG = "tag"
    }

    fun showSomeScreen() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, AuthFragment(), MAIN_TAG)
        transaction.commit()
    }

    fun hideCurrentScreen() {
        val supportFragmentManager = activity.supportFragmentManager
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentByTag(MAIN_TAG)
        if (fragment!=null) {
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            transaction.hide(fragment)
            transaction.commit()
        }
    }

    fun showCurrentScreen() {
        val supportFragmentManager = activity.supportFragmentManager
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentByTag(MAIN_TAG)
        if (fragment!=null) {
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            transaction.show(fragment)
            transaction.commit()
        }
    }

    fun showGameScreen() {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, GameScreenFragment(), MAIN_TAG)
        transaction.commit()
    }
}