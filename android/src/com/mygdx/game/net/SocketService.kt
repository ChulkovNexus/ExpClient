package com.mygdx.game.net

import android.os.Handler
import android.text.TextUtils
import com.google.gson.Gson
import com.mygdx.game.net.messages.requests.AuthMessage
import com.mygdx.game.net.messages.responses.AuthResponse
import com.mygdx.game.utils.Logger
import io.reactivex.subjects.PublishSubject
import okhttp3.*
import org.json.JSONObject

class SocketService : WebSocketListener() {

    enum class ConnectionStatus {
        CONNECTED,
        CONNECTING,
        DISCONNECTED
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
        private const val DISCONNECT_DELAY = 20000L
    }

    private var client = OkHttpClient()
    private var ws: WebSocket? = null
    private var timerHandler = Handler()
    private var connectionStatus = ConnectionStatus.DISCONNECTED
    private var callbacks = ArrayList<CallbackedMessage<BaseMessage<out BaseMessage.ApiMessage>, BaseMessage.ApiMessage>>()
    private var gson = Gson()
    val globalPublisher = PublishSubject.create<BaseMessage<BaseMessage.ApiMessage>>()

    fun connect() {
        if (connectionStatus == ConnectionStatus.DISCONNECTED) {
            val request = Request.Builder().url("ws://127.0.0.1/ws").build()
            ws = client.newWebSocket(request, this)
            connectionStatus = ConnectionStatus.CONNECTING
        }
    }

    fun disconnect() {
        timerHandler.postDelayed({
            ws?.close(NORMAL_CLOSURE_STATUS , "Activity hided")
        }, DISCONNECT_DELAY)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        connectionStatus = ConnectionStatus.CONNECTED
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        val jsonElement = JSONObject(text)
        val action = jsonElement.getString("action")
        val ns = jsonElement.getString("ns")
        val message = jsonElement.getString("message")
        if (!TextUtils.isEmpty(action)) {
            parseAsObject(action, message, ns)
        }
    }

    private fun parseAsObject(action: String, message: String, ns: String) {
        var response : BaseMessage.ApiMessage? = null
        when (action) {
            AuthMessage.ACTION -> {
                response = gson.fromJson(message, AuthResponse::class.java)
            }
        }

        if (response != null) {
            val callback = lookingForCallback(ns)
            if (callback != null) {
                callback.response.onNext(response)
            }
            postGlobalMessage(action, response)
        } else {
            Logger.log("SocketService : action not found")
        }
    }

    private fun postGlobalMessage(action: String, response: BaseMessage.ApiMessage) {
        val baseMessage = BaseMessage<BaseMessage.ApiMessage>()
        baseMessage.message = response
        baseMessage.action = action
        globalPublisher.onNext(baseMessage)
    }

    private fun lookingForCallback(ns: String): CallbackedMessage<BaseMessage<out BaseMessage.ApiMessage>, BaseMessage.ApiMessage>? {
        for (callback in callbacks) {
            if (callback.request.ns == ns)
                return callback
        }
        return null
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        connectionStatus = ConnectionStatus.DISCONNECTED
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        connectionStatus = ConnectionStatus.DISCONNECTED
    }

    fun <T: BaseMessage<out BaseMessage.ApiMessage>, D: BaseMessage.ApiMessage>sendMessage(message: CallbackedMessage<T, D>) {
        val json = gson.toJson(message.request)
        callbacks.add(message as CallbackedMessage<BaseMessage<out BaseMessage.ApiMessage>, BaseMessage.ApiMessage>)
        sendToSocket(json)
    }

    fun sendMessage(message: BaseMessage<BaseMessage.ApiMessage>) {
        val json = gson.toJson(message)
        sendToSocket(json)
    }

    private fun sendToSocket(json: String) {
        ws?.send(json)
    }
}