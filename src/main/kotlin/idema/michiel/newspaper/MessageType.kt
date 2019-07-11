package idema.michiel.newspaper

enum class MessageType(val value: String) {
    MESSAGE_TYPE("messageType"),
    SEND_GAME_STATE_TO_CLIENTS("sendGameStateToClients"),
    SEND_LOBBY_STATE_TO_CLIENTS("sendLobbyStateToClients"),
    START_GAME_TO_SERVER("startGameToServer"),
    SEND_INPUT_STATE_TO_SERVER("sendInputStateToServer"),
    CHOOSE_NAME_TO_SERVER("chooseNameToServer"),
    BACK_TO_LOBBY_TO_SERVER("backToLobbyToServer"),
    BACK_TO_LOBBY_TO_CLIENT("backToLobbyToServer"),
    CHOOSE_GAMEMODE_TO_SERVER("chooseGameModeToServer");

}