package com.mygdx.game.net

import android.text.TextUtils
import com.google.gson.Gson
import com.mygdx.game.net.messages.requests.AuthMessage
import com.mygdx.game.net.messages.requests.GetMap
import com.mygdx.game.net.messages.responses.AuthResponse
import com.mygdx.game.net.messages.responses.GetMapResponse
import com.mygdx.game.utils.Logger
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import okhttp3.*
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class SocketService : WebSocketListener() {

    enum class ConnectionStatus {
        CONNECTED,
        CONNECTING,
        DISCONNECTED
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
        private const val DISCONNECT_DELAY = 20L
        private const val RECONNECT_CHECK_TIME = 2L
    }

    private var client = OkHttpClient()
    private var ws: WebSocket? = null
    private var callbacks = ArrayList<CallbackedMessage<BaseMessage<out BaseMessage.ApiMessage>, BaseMessage.ApiMessage>>()
    private var gson = Gson()

    private var disconnectDisposable: Disposable? = null
    private var reconnectWorker: Disposable? = null
    var connectionStatus = BehaviorSubject.createDefault(ConnectionStatus.DISCONNECTED)
    val globalPublisher = PublishSubject.create<BaseMessage<BaseMessage.ApiMessage>>()

    fun connect() {
        disconnectDisposable?.dispose()
        disconnectDisposable = null

        if (connectionStatus.value == ConnectionStatus.DISCONNECTED) {
            val request = Request.Builder().url("ws://10.0.2.2:8008").build()
            ws = client.newWebSocket(request, this)
            connectionStatus.onNext(ConnectionStatus.CONNECTING)
            Logger.log("ConnectionStatus.CONNECTING")
        }
    }

    fun disconnect() {
        reconnectWorker?.dispose()
        reconnectWorker = null

        if (disconnectDisposable != null) disconnectDisposable?.dispose()
        disconnectDisposable = Observable.interval(DISCONNECT_DELAY, TimeUnit.SECONDS).subscribe {
            ws?.close(NORMAL_CLOSURE_STATUS , "Activity hided")
        }
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Logger.log("ConnectionStatus.CONNECTED")
        connectionStatus.onNext(ConnectionStatus.CONNECTED)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Logger.log("message <--- $text")
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
        response = when (action) {
            AuthMessage.ACTION -> gson.fromJson(message, AuthResponse::class.java)
            GetMap.ACTION -> gson.fromJson(message, GetMapResponse::class.java)
            else -> null
        }

        if (response != null) {
            val callback = lookingForCallback(ns)
            if (callback != null) {
                callback.response.onNext(response)
                callbacks.remove(callback)
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
        connectionStatus.onNext(ConnectionStatus.DISCONNECTED)
        Logger.log("ConnectionStatus.DISCONNECTED")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        connectionStatus.onNext(ConnectionStatus.DISCONNECTED)
        t.printStackTrace()
        Logger.log("ConnectionStatus.DISCONNECTED ${response?.message()}")

        if (reconnectWorker == null || reconnectWorker?.isDisposed == true)
            reconnectWorker = Observable.timer(RECONNECT_CHECK_TIME, TimeUnit.SECONDS).subscribe {
                if (connectionStatus.value == ConnectionStatus.DISCONNECTED) connect()
            }
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
        Logger.log("message ---> $json")
        ws?.send(json)
    }
}