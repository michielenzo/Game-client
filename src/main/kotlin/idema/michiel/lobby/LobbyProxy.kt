package idema.michiel.lobby

import idema.michiel.game.GameState
import idema.michiel.game.dto.SendGameStateToClientsDTO
import idema.michiel.lobby.dto.SendLobbyStateToClientsDTO
import idema.michiel.newspaper.network.INetworkNewsPaperSubscriber
import idema.michiel.newspaper.network.NetworkNewsPaper
import idema.michiel.utilities.DTO
import javafx.application.Platform
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle
import javafx.stage.Screen

class LobbyProxy(private val lobbyView: LobbyView): INetworkNewsPaperSubscriber {

    var isGameStarted = false

    init {
        NetworkNewsPaper.subscribe(this)
    }

    override fun notifyNetworkNews(dto: DTO) {
        when(dto){
            is SendLobbyStateToClientsDTO -> handleSendLobbyStateToClientsMessage(dto)
            is SendGameStateToClientsDTO -> handleSendGameStateToClientsMessage(dto)
        }
    }

    private fun handleSendGameStateToClientsMessage(dto: SendGameStateToClientsDTO) {
        if(!isGameStarted){
            Platform.runLater {
                    lobbyView.stage.scene = GameState(VBox(), dto)
                    Screen.getPrimary().visualBounds.also {screen ->
                        lobbyView.stage.x = (screen.maxX/2 - GameState.WIDTH/2)
                        lobbyView.stage.y = (screen.maxY/2 - GameState.HEIGHT/2)
                    }
            }
        }
        isGameStarted = true
    }

    private fun handleSendLobbyStateToClientsMessage(dto: SendLobbyStateToClientsDTO) {
        lobbyView.tableData.clear()
        dto.lobbyState.players.forEach { playerDTO ->
            lobbyView.tableData.add(TablePlayer(playerDTO.name))
        }
    }

}
