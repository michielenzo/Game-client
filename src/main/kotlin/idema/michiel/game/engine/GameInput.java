package idema.michiel.game.engine;

import idema.michiel.game.spaceBalls.GameCanvas;
import idema.michiel.game.spaceBalls.dto.BackToLobbyToServerDTO;
import idema.michiel.game.spaceBalls.dto.SendInputStateToServerDTO;
import idema.michiel.newspaper.MessageType;
import idema.michiel.newspaper.playerinput.PlayerInputNewsPaper;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GameInput {

    private GameCanvas canvas;

    GameInput(GameCanvas canvas){
        this.canvas = canvas;
    }

    public void initializeInput(){
        initializeKEYInput();
    }

    private void initializeKEYInput() {
        initializeOnKeyPressed();
        initializeOnKeyReleased();
    }

    private void initializeOnKeyPressed() {
        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                initializePressedWASDKeys(event);
                if(event.getCode().getName().equals("Esc")){
                    PlayerInputNewsPaper.INSTANCE.broadcast(new BackToLobbyToServerDTO());
                }
            }

            private void initializePressedWASDKeys(KeyEvent event) {
                final boolean wBefore = InputState.INSTANCE.getWKey();
                final boolean aBefore = InputState.INSTANCE.getAKey();
                final boolean sBefore = InputState.INSTANCE.getSKey();
                final boolean dBefore = InputState.INSTANCE.getDKey();
                flipWASDKey(event, true);
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
    }

    private void initializeOnKeyReleased() {
        canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                initializeReleaseWASDKeys(event);
                PlayerInputNewsPaper.INSTANCE.broadcast(buildSendInputStateToServerDTO());
            }

            private void initializeReleaseWASDKeys(KeyEvent event) {
                flipWASDKey(event, false);
            }
        });
    }

    private void flipWASDKey(KeyEvent event, boolean b) {
        switch (event.getCode().getName().charAt(0)){
            case 'W':
                InputState.INSTANCE.setWKey(b);
                break;
            case 'A':
                InputState.INSTANCE.setAKey(b);
                break;
            case 'S':
                InputState.INSTANCE.setSKey(b);
                break;
            case 'D':
                InputState.INSTANCE.setDKey(b);
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
