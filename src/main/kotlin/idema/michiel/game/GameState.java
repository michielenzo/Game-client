package idema.michiel.game;

import idema.michiel.game.dto.SendGameStateToClientsDTO;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GameState extends Scene {

    private GraphicsContext ctx = null;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    public GameState(VBox root, SendGameStateToClientsDTO dto) {
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
        ctx.setFill(Color.FIREBRICK);
        ctx.fillRect(20,20,100,100);
    }

}
