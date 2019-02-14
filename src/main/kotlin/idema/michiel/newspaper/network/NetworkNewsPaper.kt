package idema.michiel.newspaper.network

import idema.michiel.newspaper.INewsPaper
import idema.michiel.utilities.DTO

object NetworkNewsPaper: INewsPaper<INetworkNewsPaperSubscriber>() {

    override fun broadcast(dto: DTO) {
        subscribers.forEach {
            it.notifyNetworkNews(dto)
        }
    }

}