package idema.michiel.game;

import idema.michiel.game.dto.FireBallDTO;
import idema.michiel.game.dto.PlayerDTO;
import idema.michiel.game.dto.SendGameStateToClientsDTO;
import idema.michiel.newspaper.playerinput.PlayerInputNewsPaper;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.List;

class GameCanvas extends Canvas {

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 700;

    private GraphicsContext ctx;
    private GameView gameView;

    GameCanvas(GameView gameView){
        super(WIDTH, HEIGHT);
        this.gameView = gameView;
        setupInput();
        ctx = getGraphicsContext2D();
    }

    private void setupInput() {
        System.out.println("setupInput");
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                final boolean wBefore = InputState.INSTANCE.getWKey();
                final boolean aBefore = InputState.INSTANCE.getAKey();
                final boolean sBefore = InputState.INSTANCE.getSKey();
                final boolean dBefore = InputState.INSTANCE.getDKey();
                switch (event.getCode().getName().charAt(0)){
                    case 'W':
                        System.out.println("W");
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
                    PlayerInputNewsPaper.INSTANCE.broadcast(gameView.buildSendInputStateToServerDTO());
                }
            }
        });
        setOnKeyReleased(new EventHandler<KeyEvent>() {
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
                PlayerInputNewsPaper.INSTANCE.broadcast(gameView.buildSendInputStateToServerDTO());
            }
        });
    }

    void renderGameState(SendGameStateToClientsDTO dto) {
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

}
