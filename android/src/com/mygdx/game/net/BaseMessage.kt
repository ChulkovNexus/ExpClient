package com.mygdx.game.net

class BaseMessage<Request : BaseMessage.ApiMessage> {

    interface ApiMessage

    var v = 0
    var action = ""
    var ns = ""
    var token : String? = null
    var message: Request? = null
}

