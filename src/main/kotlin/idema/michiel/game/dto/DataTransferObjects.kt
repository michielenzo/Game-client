package idema.michiel.game.dto

import idema.michiel.newspaper.MessageType
import idema.michiel.utilities.DTO

data class SendGameStateToClientsDTO(val gameState: GameStateDTO,
                                     val messageType: String = MessageType.SEND_GAME_STATE_TO_CLIENTS.value): DTO()

data class GameStateDTO(val players: MutableList<PlayerDTO> = mutableListOf()): DTO()

data class PlayerDTO(val sessionId: String, @Volatile var xPosition: Int, @Volatile var yPosition: Int): DTO()