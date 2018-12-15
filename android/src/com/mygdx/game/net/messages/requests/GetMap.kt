package com.mygdx.game.net.messages.requests

import com.mygdx.game.net.BaseMessage
import com.mygdx.game.net.CallbackedMessage
import com.mygdx.game.net.messages.responses.GetMapResponse

class GetMap: BaseMessage.ApiMessage {

    companion object {
        const val ACTION = "get_map"

        fun createCallbackedMessage(token: String): CallbackedMessage<BaseMessage<GetMap>, GetMapResponse> {
            val getMapMessage = GetMap()
            getMapMessage.token = token
            val baseMessage = BaseMessage<GetMap>()
            baseMessage.action = GetMap.ACTION
            baseMessage.message = getMapMessage
            return CallbackedMessage(baseMessage)
        }
    }

    var token : String = ""

}