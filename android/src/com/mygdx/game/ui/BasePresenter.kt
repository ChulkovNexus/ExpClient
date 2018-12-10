package com.mygdx.game.ui

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<V> (var v: V) {

    val subscribers = CompositeDisposable()

    open fun onStart() {

    }

    open fun onPause() {
        subscribers.dispose()
        subscribers.clear()
    }
}