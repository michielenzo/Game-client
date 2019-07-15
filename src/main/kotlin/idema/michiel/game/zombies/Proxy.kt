package idema.michiel.game.zombies

import idema.michiel.game.zombies.dto.SendZombiesGameStateToClientsDTO
import idema.michiel.newspaper.network.INetworkNewsPaperSubscriber
import idema.michiel.newspaper.network.NetworkNewsPaper
import idema.michiel.utilities.DTO

class Proxy(val gameCanvas: GameCanvas): INetworkNewsPaperSubscriber {

    init {
        NetworkNewsPaper.subscribe(this)
    }

    override fun notifyNetworkNews(dto: DTO) {
        when(dto){
            is SendZombiesGameStateToClientsDTO -> handleGameStateToClientsMessage(dto)
        }
    }

    private fun handleGameStateToClientsMessage(dto: SendZombiesGameStateToClientsDTO) {
        gameCanvas.render(dto)
    }
}