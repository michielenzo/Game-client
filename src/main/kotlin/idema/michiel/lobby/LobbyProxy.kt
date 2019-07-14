package idema.michiel.lobby

import idema.michiel.game.spaceBalls.dto.BackToLobbyToClientDTO
import idema.michiel.game.spaceBalls.dto.SendSpaceBallsGameStateToClientsDTO
import idema.michiel.game.zombies.SendZombiesGameStateToClientsDTO
import idema.michiel.lobby.dto.SendLobbyStateToClientsDTO
import idema.michiel.newspaper.network.INetworkNewsPaperSubscriber
import idema.michiel.newspaper.network.NetworkNewsPaper
import idema.michiel.utilities.DTO
import javafx.application.Platform
import javafx.scene.layout.VBox
import javafx.stage.Screen

class LobbyProxy(private val lobbyView: LobbyView): INetworkNewsPaperSubscriber {

    var isGameStarted = false

    init {
        NetworkNewsPaper.subscribe(this)
    }

    override fun notifyNetworkNews(dto: DTO) {
        when(dto){
            is SendLobbyStateToClientsDTO -> handleSendLobbyStateToClientsMessage(dto)
            is SendSpaceBallsGameStateToClientsDTO -> handleSendSpaceBallsGameStateToClientsMessage(dto)
            is SendZombiesGameStateToClientsDTO -> handleSendZombiesGameStateToClientsMessage(dto)
            is BackToLobbyToClientDTO -> handleBackToLobbyToClientMessage(dto)
        }
    }

    private fun handleSendZombiesGameStateToClientsMessage(dto: SendZombiesGameStateToClientsDTO) {
        if(!isGameStarted){
            Platform.runLater {
                lobbyView.stage.scene = idema.michiel.game.zombies.GameView(VBox())
                Screen.getPrimary().visualBounds.also {screen ->
                    lobbyView.stage.x = (screen.maxX/2 - idema.michiel.game.zombies.GameView.WIDTH/2)
                    lobbyView.stage.y = (screen.maxY/2 - idema.michiel.game.zombies.GameView.HEIGHT/2)
                }
            }
        }
        isGameStarted = true
    }

    private fun handleSendSpaceBallsGameStateToClientsMessage(dtoSpaceBalls: SendSpaceBallsGameStateToClientsDTO) {
        if(!isGameStarted){
            Platform.runLater {
                lobbyView.stage.scene = idema.michiel.game.spaceBalls.GameView(VBox())
                Screen.getPrimary().visualBounds.also {screen ->
                    lobbyView.stage.x = (screen.maxX/2 - idema.michiel.game.spaceBalls.GameView.WIDTH/2)
                    lobbyView.stage.y = (screen.maxY/2 - idema.michiel.game.spaceBalls.GameView.HEIGHT/2)
                }
            }
        }
        isGameStarted = true
    }

    private fun handleBackToLobbyToClientMessage(dto: BackToLobbyToClientDTO) {
        isGameStarted = false
        Platform.runLater { lobbyView.stage.scene = lobbyView.scene }
    }

    private fun handleSendLobbyStateToClientsMessage(dto: SendLobbyStateToClientsDTO) {
        lobbyView.tableData.clear()
        dto.lobbyState.players.forEach { playerDTO ->
            lobbyView.tableData.add(TablePlayer(playerDTO.name, playerDTO.status))
        }
        Platform.runLater {
            lobbyView.lobbyLabelProperty.set(dto.lobbyState.gameMode)
        }
    }

}
