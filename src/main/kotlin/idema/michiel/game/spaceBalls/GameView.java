package idema.michiel.game.spaceBalls;

import idema.michiel.game.spaceBalls.dto.*;
import idema.michiel.lobby.LobbyView;
import idema.michiel.newspaper.MessageType;
import idema.michiel.newspaper.network.INetworkNewsPaperSubscriber;
import idema.michiel.newspaper.network.NetworkNewsPaper;
import idema.michiel.utilities.DTO;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class GameView extends Scene implements INetworkNewsPaperSubscriber {

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 700;
    private GameCanvas gameCanvas;
    LobbyView lobbyView;

    public GameView(VBox root) {
        super(root);
        this.lobbyView = lobbyView;
        NetworkNewsPaper.INSTANCE.subscribe(this);
        gameCanvas = new GameCanvas(this);
        root.getChildren().add(gameCanvas);
        gameCanvas.requestFocus();
    }

    public void notifyNetworkNews(@NotNull DTO dto) {
        if(dto instanceof SendSpaceBallsGameStateToClientsDTO){
            if(gameCanvas != null)gameCanvas.render((SendSpaceBallsGameStateToClientsDTO)dto);
        }
    }

    SendInputStateToServerDTO buildSendInputStateToServerDTO() {
        return new SendInputStateToServerDTO(
                InputState.INSTANCE.getWKey(),
                InputState.INSTANCE.getAKey(),
                InputState.INSTANCE.getSKey(),
                InputState.INSTANCE.getDKey(),
                MessageType.SEND_INPUT_STATE_TO_SERVER.getValue());
    }


}
