package idema.michiel.newspaper

import idema.michiel.utilities.DTO

abstract class INewsPaper<ISubscriber> {

    protected val subscribers: MutableList<ISubscriber> = mutableListOf()

    abstract fun broadcast(dto: DTO)

    fun subscribe(newSubscriber: ISubscriber) {
        subscribers.add(newSubscriber)
    }

}