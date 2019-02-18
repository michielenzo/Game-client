package idema.michiel.game;

import idema.michiel.game.dto.*;
import idema.michiel.newspaper.MessageType;
import idema.michiel.newspaper.network.INetworkNewsPaperSubscriber;
import idema.michiel.newspaper.network.NetworkNewsPaper;
import idema.michiel.newspaper.playerinput.PlayerInputNewsPaper;
import idema.michiel.utilities.DTO;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GameView extends Scene implements INetworkNewsPaperSubscriber {

    private GraphicsContext ctx = null;

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 700;

    public GameView(VBox root, SendGameStateToClientsDTO dto) {
        super(root);
        NetworkNewsPaper.INSTANCE.subscribe(this);
        gameCanvas(root);
    }

    public void notifyNetworkNews(@NotNull DTO dto) {
        if(dto instanceof SendGameStateToClientsDTO){
            if(ctx != null)renderGameState((SendGameStateToClientsDTO)dto);
        }
    }

    private void gameCanvas(VBox root) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                final boolean wBefore = InputState.INSTANCE.getWKey();
                final boolean aBefore = InputState.INSTANCE.getAKey();
                final boolean sBefore = InputState.INSTANCE.getSKey();
                final boolean dBefore = InputState.INSTANCE.getDKey();
                switch (event.getCode().getName().charAt(0)){
                    case 'W':
                        InputState.INSTANCE.setWKey(true);
                        break;
                    case 'A':
                        InputState.INSTANCE.setAKey(true);
                        break;
                    case 'S':
                        InputState.INSTANCE.setSKey(true);
                        break;
                    case 'D':
                        InputState.INSTANCE.setDKey(true);
                }
                final boolean wAfter = InputState.INSTANCE.getWKey();
                final boolean aAfter = InputState.INSTANCE.getAKey();
                final boolean sAfter = InputState.INSTANCE.getSKey();
                final boolean dAfter = InputState.INSTANCE.getDKey();
                if(wBefore != wAfter || aBefore != aAfter ||
                   sBefore != sAfter || dBefore != dAfter
                ){
                    PlayerInputNewsPaper.INSTANCE.broadcast(buildSendInputStateToServerDTO());
                }
            }
        });

        canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode().getName().charAt(0)){
                    case 'W':
                        InputState.INSTANCE.setWKey(false);
                        break;
                    case 'A':
                        InputState.INSTANCE.setAKey(false);
                        break;
                    case 'S':
                        InputState.INSTANCE.setSKey(false);
                        break;
                    case 'D':
                        InputState.INSTANCE.setDKey(false);
                }
                PlayerInputNewsPaper.INSTANCE.broadcast(buildSendInputStateToServerDTO());
            }
        });

        ctx = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        canvas.requestFocus();
    }

    private void renderGameState(SendGameStateToClientsDTO dto) {
        background();
        renderPlayers(dto.getGameState().getPlayers());
        renderFireBalls(dto.getGameState().getFireBalls());
    }



    private void background() {
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0,0,WIDTH,HEIGHT);
    }

    private void renderPlayers(List<PlayerDTO> players) {
        ctx.setFill(Color.BLUEVIOLET);
        for(PlayerDTO player: players){
            ctx.fillRect(player.getXPosition(),
                         player.getYPosition(),
                         player.getWidth(),
                         player.getHeight());
        }
    }

    private void renderFireBalls(List<FireBallDTO> fireBalls) {
        ctx.setFill(Color.FIREBRICK);
        for(FireBallDTO fireBall: fireBalls){
            ctx.fillOval(fireBall.getXPosition() - fireBall.getDiameter()/2,
                         fireBall.getYPosition() - fireBall.getDiameter()/2,
                         fireBall.getDiameter(),
                         fireBall.getDiameter());
        }
    }

    private SendInputStateToServerDTO buildSendInputStateToServerDTO() {
        return new SendInputStateToServerDTO(
                InputState.INSTANCE.getWKey(),
                InputState.INSTANCE.getAKey(),
                InputState.INSTANCE.getSKey(),
                InputState.INSTANCE.getDKey(),
                MessageType.SEND_INPUT_STATE_TO_SERVER.getValue());
    }


}
