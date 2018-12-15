package com.mygdx.game.net.messages.requests

import com.mygdx.game.net.BaseMessage
import com.mygdx.game.net.CallbackedMessage
import com.mygdx.game.net.messages.responses.AuthResponse

class AuthMessage : BaseMessage.ApiMessage {

    companion object {
        const val ACTION = "login_user"

        fun createCallbackedMessage(login: String, password: String): CallbackedMessage<BaseMessage<AuthMessage>, AuthResponse> {

            val authMessage = AuthMessage()
            authMessage.login = login
            authMessage.password = password
            val baseMessage = BaseMessage<AuthMessage>()
            baseMessage.action = AuthMessage.ACTION
            baseMessage.message = authMessage
            return CallbackedMessage(baseMessage)
        }
    }

//request
    var login = ""
    var password = ""

}