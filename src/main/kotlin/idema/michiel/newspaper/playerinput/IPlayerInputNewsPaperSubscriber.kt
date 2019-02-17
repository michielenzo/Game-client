package idema.michiel.newspaper.playerinput

import idema.michiel.utilities.DTO

interface IPlayerInputNewsPaperSubscriber {
    fun notifyPlayerInputNews(dto: DTO)
}