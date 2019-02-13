package idema.michiel.network

import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest
import org.eclipse.jetty.websocket.client.WebSocketClient
import java.lang.Exception
import java.net.URI

@WebSocket
class WebSocketClientEndPoint{

    private val serverUri = "ws://localhost:8080/player"
    private lateinit var session: Session

    init {
        try {
            WebSocketClient().also { clientServer ->
                clientServer.start()
                clientServer.connect(
                        this,
                        URI(serverUri),
                        ClientUpgradeRequest())
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    @OnWebSocketConnect
    fun onConnect(session: Session){
        this.session = session
    }

    @OnWebSocketMessage
    fun onMessage(message: String){
        println(message)
    }

    @OnWebSocketClose
    fun onClose(statusCode: Int, reason: String){

    }

}