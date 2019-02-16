package idema.michiel.game;

import idema.michiel.game.dto.PlayerDTO;
import idema.michiel.game.dto.SendGameStateToClientsDTO;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
        ctx = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
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

}
