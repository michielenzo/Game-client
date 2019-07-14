package idema.michiel.game.spaceBalls.dto

import idema.michiel.newspaper.MessageType
import idema.michiel.utilities.DTO

data class SendGameStateToClientsDTO(val gameState: GameStateDTO,
                                     val messageType: String = MessageType.SEND_GAME_STATE_TO_CLIENTS.value): DTO()

data class GameStateDTO(val players: MutableList<PlayerDTO> = mutableListOf(),
                        val fireBalls: MutableList<FireBallDTO> = mutableListOf(),
                        val powerUps: MutableList<PowerUpDTO> = mutableListOf()): DTO()

data class PlayerDTO(val sessionId: String,
                     val name: String,
                     @Volatile var xPosition: Int,
                     @Volatile var yPosition: Int,
                     val health: Int,
                     val hasShield: Boolean,
                     val width: Int,
                     val height: Int): DTO()

data class FireBallDTO(val xPosition: Int,
                       val yPosition: Int,
                       val diameter: Int)

data class PowerUpDTO(val type: String,
                      val xPosition: Int,
                      val yPosition: Int,
                      val width: Int,
                      val height: Int): DTO()

data class SendInputStateToServerDTO(val wKey: Boolean,
                                     val aKey: Boolean,
                                     val sKey: Boolean,
                                     val dKey: Boolean,
                                     val messageType: String = MessageType.SEND_INPUT_STATE_TO_SERVER.value): DTO()

data class BackToLobbyToServerDTO(val messageType: String = MessageType.BACK_TO_LOBBY_TO_SERVER.value): DTO()

data class BackToLobbyToClientDTO(val messageType: String = MessageType.BACK_TO_LOBBY_TO_CLIENT.value): DTO()