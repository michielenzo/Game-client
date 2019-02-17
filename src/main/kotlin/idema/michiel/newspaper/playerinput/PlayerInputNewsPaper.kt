package idema.michiel.newspaper.playerinput

import idema.michiel.newspaper.INewsPaper
import idema.michiel.utilities.DTO

object PlayerInputNewsPaper: INewsPaper<IPlayerInputNewsPaperSubscriber>(){

    override fun broadcast(dto: DTO) {
        subscribers.forEach {
            it.notifyPlayerInputNews(dto)
        }
    }

}