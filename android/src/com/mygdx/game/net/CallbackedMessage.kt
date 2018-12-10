package com.mygdx.game.net

import io.reactivex.subjects.PublishSubject

class CallbackedMessage<Request:BaseMessage<out BaseMessage.ApiMessage>, Response:BaseMessage.ApiMessage> (var request : Request) {

    val response = PublishSubject.create<Response>()
}