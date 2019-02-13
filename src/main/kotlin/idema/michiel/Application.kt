package idema.michiel

import idema.michiel.lobby.LobbyView
import idema.michiel.network.WebSocketClientEndPoint

fun main(args: Array<String>){
    WebSocketClientEndPoint()
    LobbyView.launch()
}