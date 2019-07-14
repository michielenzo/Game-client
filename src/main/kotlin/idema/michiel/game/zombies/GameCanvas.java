package idema.michiel.game.zombies;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameCanvas extends Canvas {

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 700;

    private GraphicsContext ctx;
    GameView gameView;

    GameCanvas(GameView gameView){
        super(WIDTH, HEIGHT);
        this.gameView = gameView;
        ctx = getGraphicsContext2D();
    }
}
