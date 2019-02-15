package idema.michiel.newspaper.lobby

import idema.michiel.utilities.DTO

interface ILobbyNewsPaperSubscriber {
    fun notifyLobbyNews(dto: DTO)
}