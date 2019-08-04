package idema.michiel.game.zombies;

import idema.michiel.game.engine.input.GameInput;
import idema.michiel.game.zombies.dto.PlayerDTO;
import idema.michiel.game.zombies.dto.SendZombiesGameStateToClientsDTO;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class GameCanvas extends Canvas {

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 700;

    private GraphicsContext ctx;
    private GameView gameView;
    private GameInput canvasInput = new GameInput(this);

    GameCanvas(GameView gameView){
        super(WIDTH, HEIGHT);
        this.gameView = gameView;
        ctx = getGraphicsContext2D();
    }

    public void render(SendZombiesGameStateToClientsDTO dto) {
        renderPlayers(dto.getGameState().getPlayers());
    }

    private void renderPlayers(List<PlayerDTO> players) {
        for(PlayerDTO player: players){
            ctx.setFill(Color.BLACK);
            ctx.fillOval(player.getXPos(), player.getYPos(), 50, 50);
        }
    }
}
