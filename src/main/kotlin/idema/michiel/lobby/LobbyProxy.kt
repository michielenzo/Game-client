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
//        TODO(this function throws an error right now, the problem is that the lobby gets messages from the network before the lobby is initialized.)

//        lobbyView.tableData.removeAll()
//        dto.lobbyState.players.forEach { playerDTO ->
//            lobbyView.tableData.add(TablePlayer(playerDTO.name))
//        }
    }

}