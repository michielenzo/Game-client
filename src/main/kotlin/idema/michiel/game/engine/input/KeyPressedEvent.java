package idema.michiel.game.engine.input;


import idema.michiel.game.spaceBalls.dto.BackToLobbyToServerDTO;
import idema.michiel.newspaper.playerinput.PlayerInputNewsPaper;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyPressedEvent implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
        handlePressedWASDKeys(event);
        if(event.getCode().getName().equals("Esc")){
            PlayerInputNewsPaper.INSTANCE.broadcast(new BackToLobbyToServerDTO());
        }
    }

    private void handlePressedWASDKeys(KeyEvent event) {
        final boolean wBefore = InputState.INSTANCE.getWKey();
        final boolean aBefore = InputState.INSTANCE.getAKey();
        final boolean sBefore = InputState.INSTANCE.getSKey();
        final boolean dBefore = InputState.INSTANCE.getDKey();
        GameInput.flipWASDKey(event, true);
        final boolean wAfter = InputState.INSTANCE.getWKey();
        final boolean aAfter = InputState.INSTANCE.getAKey();
        final boolean sAfter = InputState.INSTANCE.getSKey();
        final boolean dAfter = InputState.INSTANCE.getDKey();
        if(wBefore != wAfter || aBefore != aAfter ||
                sBefore != sAfter || dBefore != dAfter
        ){
            PlayerInputNewsPaper.INSTANCE.broadcast(GameInput.buildSendInputStateToServerDTO());
        }
    }

}
