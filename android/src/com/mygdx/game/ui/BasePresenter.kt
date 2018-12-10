package com.mygdx.game.ui

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<V> (var v: V) {

    val subscribers = CompositeDisposable()

    fun onStart() {

    }

    fun onPause() {

    }
}