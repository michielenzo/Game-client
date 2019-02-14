package idema.michiel.lobby

import idema.michiel.lobby.dto.SendLobbyStateToClientsDTO
import idema.michiel.newspaper.network.INetworkNewsPaperSubscriber
import idema.michiel.newspaper.network.NetworkNewsPaper
import idema.michiel.utilities.DTO

class LobbyProxy(private val lobbyView: LobbyView): INetworkNewsPaperSubscriber {

    init {
        NetworkNewsPaper.subscribe(this)
    }

    override fun notifyNetworkNews(dto: DTO) {
        when(dto){
            is SendLobbyStateToClientsDTO -> handleSendLobbyStateToClientsMessage(dto)
        }
    }

    private fun handleSendLobbyStateToClientsMessage(dto: SendLobbyStateToClientsDTO) {
        lobbyView.tableData.clear()
        dto.lobbyState.players.forEach { playerDTO ->
            lobbyView.tableData.add(TablePlayer(playerDTO.name))
        }
    }

}
