package idema.michiel.game;

import idema.michiel.game.dto.PlayerDTO;
import idema.michiel.game.dto.SendGameStateToClientsDTO;
import idema.michiel.game.dto.SendInputStateToServerDTO;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.EventListener;
import java.util.List;

public class GameView extends Scene {

    private GraphicsContext ctx = null;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    public GameView(VBox root, SendGameStateToClientsDTO dto) {
        super(root);
        gameCanvas(root);
        renderGameState(dto);
    }

    private void gameCanvas(VBox root) {
        Canvas canvas = new Canvas(800, 500);

        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
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
                buildSendInputStateToServerDTO();//TODO broadcast
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
                buildSendInputStateToServerDTO();//TODO broadcast
            }
        });

        ctx = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        canvas.requestFocus();
    }

    private void renderGameState(SendGameStateToClientsDTO dto) {
        renderPlayers(dto.getGameState().getPlayers());
    }

    private void renderPlayers(List<PlayerDTO> players) {
        ctx.setFill(Color.FIREBRICK);
        for(PlayerDTO player: players){
            ctx.fillRect(player.getXPosition(), player.getYPosition(), player.getWidth(), player.getHeight());
        }
    }

    private SendInputStateToServerDTO buildSendInputStateToServerDTO() {
        return new SendInputStateToServerDTO(
                InputState.INSTANCE.getWKey(),
                InputState.INSTANCE.getAKey(),
                InputState.INSTANCE.getSKey(),
                InputState.INSTANCE.getDKey());
    }



}
