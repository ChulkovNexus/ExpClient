package com.mygdx.game.utils

import android.util.Log
import com.mygdx.game.BuildConfig

class Logger {
    companion object {

        fun log(text: String) {
            if (BuildConfig.BUILD_TYPE.contentEquals("debug") || BuildConfig.BUILD_TYPE.contentEquals("staging"))
                Log.d("SSSSSSSSSSSS", text)
        }
    }
}