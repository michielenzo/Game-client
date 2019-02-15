package idema.michiel.newspaper.lobby

import idema.michiel.newspaper.INewsPaper
import idema.michiel.utilities.DTO

object LobbyNewsPaper: INewsPaper<ILobbyNewsPaperSubscriber>() {

    override fun broadcast(dto: DTO) {
        subscribers.forEach {
            it.notifyLobbyNews(dto)
        }
    }

}