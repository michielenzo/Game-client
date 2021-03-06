package idema.michiel.network

import com.google.gson.Gson
import idema.michiel.game.spaceBalls.dto.BackToLobbyToClientDTO
import idema.michiel.game.spaceBalls.dto.SendSpaceBallsGameStateToClientsDTO
import idema.michiel.game.zombies.dto.SendZombiesGameStateToClientsDTO
import idema.michiel.lobby.dto.ChooseGameModeToServerDTO
import idema.michiel.lobby.dto.ChooseNameToServerDTO
import idema.michiel.lobby.dto.SendLobbyStateToClientsDTO
import idema.michiel.lobby.dto.StartGameToServerDTO
import idema.michiel.newspaper.MessageType
import idema.michiel.newspaper.lobby.ILobbyNewsPaperSubscriber
import idema.michiel.newspaper.lobby.LobbyNewsPaper
import idema.michiel.newspaper.network.NetworkNewsPaper
import idema.michiel.newspaper.playerinput.IPlayerInputNewsPaperSubscriber
import idema.michiel.newspaper.playerinput.PlayerInputNewsPaper
import idema.michiel.utilities.DTO
import idema.michiel.utilities.JSON
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest
import org.eclipse.jetty.websocket.client.WebSocketClient
import java.net.URI

@WebSocket
class WebSocketClientEndPoint: ILobbyNewsPaperSubscriber, IPlayerInputNewsPaperSubscriber{

    private val serverUri = "ws://192.168.1.226:8080/player"
    private lateinit var session: Session

    init {
        LobbyNewsPaper.subscribe(this)
        PlayerInputNewsPaper.subscribe(this)
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
        buildDTO(message).also {
            it?: return
            NetworkNewsPaper.broadcast(it)
        }
    }

    @OnWebSocketClose
    fun onClose(statusCode: Int, reason: String){

    }

    override fun notifyLobbyNews(dto: DTO) {
        when(dto){
            is StartGameToServerDTO -> session.remote.sendString(JSON.convertDTOObjectToString(dto))
            is ChooseNameToServerDTO -> session.remote.sendString(JSON.convertDTOObjectToString(dto))
            is ChooseGameModeToServerDTO -> session.remote.sendString(JSON.convertDTOObjectToString(dto))
        }

    }

    override fun notifyPlayerInputNews(dto: DTO) {
        session.remote.sendString(JSON.convertDTOObjectToString(dto))
    }


    private fun buildDTO(message: String): DTO? {
        JSON.convertStringToGSONObject(message).get(MessageType.MESSAGE_TYPE.value).asString.also{
            return when (it) {
                MessageType.SEND_LOBBY_STATE_TO_CLIENTS.value -> Gson().fromJson(message,
                        SendLobbyStateToClientsDTO::class.java)
                MessageType.SEND_SPACE_BALLS_GAME_STATE_TO_CLIENTS.value -> Gson().fromJson(message,
                        SendSpaceBallsGameStateToClientsDTO::class.java)
                MessageType.BACK_TO_LOBBY_TO_CLIENT.value -> Gson().fromJson(message,
                        BackToLobbyToClientDTO::class.java)
                MessageType.SEND_ZOMBIES_GAME_STATE_TO_CLIENTS.value -> Gson().fromJson(message,
                        SendZombiesGameStateToClientsDTO::class.java)
                else -> {
                    throw Exception(String()
                            .plus("Invalid message received: ")
                            .plus(message)
                            .plus("\n"))
                }
            }
        }
        return null
    }

}