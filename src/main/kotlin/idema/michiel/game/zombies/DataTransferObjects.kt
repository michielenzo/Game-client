package idema.michiel.game.zombies

import idema.michiel.newspaper.MessageType
import idema.michiel.utilities.DTO

data class SendZombiesGameStateToClientsDTO(val gameState: GameStateDTO,
                                            val messageType: String = MessageType.SEND_ZOMBIES_GAME_STATE_TO_CLIENTS.value): DTO()

data class GameStateDTO(val a: Int): DTO()