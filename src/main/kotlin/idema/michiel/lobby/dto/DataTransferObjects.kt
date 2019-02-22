package idema.michiel.lobby.dto

import idema.michiel.newspaper.MessageType
import idema.michiel.utilities.DTO

data class SendLobbyStateToClientsDTO(val lobbyState: LobbyStateDTO,
                                      val messageType: String = MessageType.SEND_LOBBY_STATE_TO_CLIENTS.value): DTO()

data class LobbyStateDTO(val players: MutableList<PlayerDTO> = mutableListOf()): DTO()

data class PlayerDTO(val id: String, val status: String, val name: String = id): DTO()

data class StartGameToServerDTO(val messageType: String = MessageType.START_GAME_TO_SERVER.value): DTO()

data class ChooseNameToServerDTO(val playerId: String?,
                                 val chosenName: String,
                                 val messageType: String = MessageType.CHOOSE_NAME_TO_SERVER.value): DTO()