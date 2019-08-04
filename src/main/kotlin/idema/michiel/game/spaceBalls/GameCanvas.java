package idema.michiel.game.spaceBalls;

import idema.michiel.game.engine.input.GameInput;
import idema.michiel.game.engine.ImageLoader;
import idema.michiel.game.spaceBalls.dto.FireBallDTO;
import idema.michiel.game.spaceBalls.dto.PlayerDTO;
import idema.michiel.game.spaceBalls.dto.PowerUpDTO;
import idema.michiel.game.spaceBalls.dto.SendSpaceBallsGameStateToClientsDTO;
import idema.michiel.lobby.LobbyView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.List;

public class GameCanvas extends Canvas {

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 700;

    private GraphicsContext ctx;
    private GameInput canvasInput = new GameInput(this);
    GameView gameView;

    private Image heartImage;
    private final int heartWidth = 25;
    private final int heartHeight = 25;

    private Image deathPlayerImage;
    private Image backgroundImage;
    private Image medKitImage;
    private Image shieldPowerUpImage;
    private Image shieldPlayerImage;
    private Image inverterImage;

    private final int playerLivesDivX = 20;
    private final int playerLivesDivY = 35;

    GameCanvas(GameView gameView){
        super(WIDTH, HEIGHT);
        this.gameView = gameView;
        ctx = getGraphicsContext2D();
        canvasInput.initializeInput();
        loadImages();
    }

    private void loadImages() {
        heartImage = ImageLoader.INSTANCE.load("/images/heart.jpg", heartWidth, heartHeight);
        backgroundImage = ImageLoader.INSTANCE.load("/images/space3.png", WIDTH, HEIGHT);
        medKitImage = ImageLoader.INSTANCE.load("/images/medkit.png", 40, 40);
        shieldPlayerImage = ImageLoader.INSTANCE.load("/images/rsshield.png", 74, 74);
        shieldPowerUpImage = ImageLoader.INSTANCE.load("/images/rsshield.png", 54, 54);
        inverterImage = ImageLoader.INSTANCE.load("/images/arrows.png", 40, 40);
        deathPlayerImage = ImageLoader.INSTANCE.load("/images/skull.png", 65, 65);
    }

    void render(SendSpaceBallsGameStateToClientsDTO dto){
        renderGameState(dto);
        renderHUD(dto);
    }

    private void renderGameState(SendSpaceBallsGameStateToClientsDTO dto) {
        background();
        renderPowerUps(dto.getGameState().getPowerUps());
        renderPlayers(dto.getGameState().getPlayers());
        renderFireBalls(dto.getGameState().getFireBalls());
    }

    private void renderPowerUps(List<PowerUpDTO> powerUps) {
        ctx.setGlobalAlpha(1);
        if(powerUps.size() > 0){
            for(PowerUpDTO powerUp: powerUps){
                if(powerUp.getType().equals("med_kit")){
                    renderMedKit(powerUp);
                }else if(powerUp.getType().equals("shield")){
                    renderShield(powerUp);
                }else if(powerUp.getType().equals("inverter")){
                    renderInverter(powerUp);
                }
            }
        }
    }

    private void renderInverter(PowerUpDTO powerUp) {
        ctx.drawImage(inverterImage,
                powerUp.getXPosition(),
                powerUp.getYPosition());
    }

    private void renderShield(PowerUpDTO powerUp) {
        ctx.setFill(Color.WHITE);
        ctx.drawImage(shieldPowerUpImage,
                powerUp.getXPosition()-7,
                powerUp.getYPosition()-7);
    }

    private void renderMedKit(PowerUpDTO powerUp) {
        ctx.setFill(Color.WHITE);
        ctx.fillRect(powerUp.getXPosition(),
                powerUp.getYPosition() + 7,
                powerUp.getWidth(),
                powerUp.getHeight() - 7);
        ctx.drawImage(medKitImage,
                powerUp.getXPosition(),
                powerUp.getYPosition());
    }

    private void renderHUD(SendSpaceBallsGameStateToClientsDTO dto){
        final List<PlayerDTO> players = dto.getGameState().getPlayers();
        final int marginRightHeart = 7;
        final int marginBottomHeart = 15;
        final int marginBottomName = 5;
        ctx.setGlobalAlpha(0.5);
        ctx.setFill(Color.WHITE);
        for(int i = 0; i < players.size(); i++){
            String name = players.get(i).getName();
            if(name.length() > LobbyView.MAX_LENGTH_PLAYER_NAME)
                name = name.substring(0,LobbyView.MAX_LENGTH_PLAYER_NAME - 1);
            ctx.fillText(name,
                         playerLivesDivX,
                      playerLivesDivY-marginBottomName + i*(heartHeight+marginBottomHeart));
        }

        for(int i = 0; i < players.size(); i++){
            for(int j = 0; j < players.get(i).getHealth(); j++){
                ctx.drawImage(heartImage,
                           playerLivesDivX + j*(heartWidth+marginRightHeart),
                           playerLivesDivY + i*(heartHeight+marginBottomHeart));
            }
        }
    }

    private void background() {
        ctx.setGlobalAlpha(1);
        ctx.drawImage(backgroundImage,0,0);
    }

    private void renderPlayers(List<PlayerDTO> players) {
        ctx.setGlobalAlpha(1);
        for(PlayerDTO player: players){
            if(player.getHealth() <= 0){
                ctx.setFill(Color.WHITE);
                ctx.fillText(player.getName(),player.getXPosition(),player.getYPosition() - 10);
                ctx.drawImage(deathPlayerImage, player.getXPosition(), player.getYPosition());
            }else{
                ctx.setFill(Color.WHITE);
                ctx.fillText(player.getName(),player.getXPosition(),player.getYPosition() - 10);
                ctx.setFill(Color.BLUEVIOLET);
                ctx.fillRect(player.getXPosition(),
                        player.getYPosition(),
                        player.getWidth(),
                        player.getHeight());
                if(player.getHasShield()){
                    ctx.drawImage(shieldPlayerImage, player.getXPosition() - 13, player.getYPosition() - 13);
                }
            }
        }
    }

    private void renderFireBalls(List<FireBallDTO> fireBalls) {
        ctx.setGlobalAlpha(1);
        ctx.setFill(Color.FIREBRICK);
        for(FireBallDTO fireBall: fireBalls){
            ctx.fillOval(fireBall.getXPosition() - fireBall.getDiameter()/2,
                    fireBall.getYPosition() - fireBall.getDiameter()/2,
                    fireBall.getDiameter(),
                    fireBall.getDiameter());
        }
    }

}
