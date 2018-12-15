package com.mygdx.game.net.messages.responses

import com.mygdx.game.models.MapTile
import com.mygdx.game.net.BaseMessage

class GetMapResponse : BaseMessage.ApiMessage {

    var world = ArrayList<ArrayList<MapTile>>()

}