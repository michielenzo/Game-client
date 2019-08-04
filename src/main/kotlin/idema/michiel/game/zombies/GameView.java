package idema.michiel.game.zombies;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class GameView extends Scene {

    public static final int WIDTH = 1100;
    public static final int HEIGHT = 700;

    private GameCanvas gameCanvas;
    private Proxy proxy;

    public GameView(VBox root) {
        super(root);
        gameCanvas = new GameCanvas(this);
        root.getChildren().add(gameCanvas);
        gameCanvas.requestFocus();
        proxy = new Proxy(gameCanvas);
    }
}
