package idema.michiel.newspaper.network

import idema.michiel.utilities.DTO

interface INetworkNewsPaperSubscriber {
    fun notifyNetworkNews(dto: DTO)
}