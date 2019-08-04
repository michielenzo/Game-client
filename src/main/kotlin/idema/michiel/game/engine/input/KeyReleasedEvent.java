package idema.michiel.game.engine.input;

import idema.michiel.newspaper.playerinput.PlayerInputNewsPaper;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import static idema.michiel.game.engine.input.GameInput.buildSendInputStateToServerDTO;
import static idema.michiel.game.engine.input.GameInput.flipWASDKey;

public class KeyReleasedEvent implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent event) {
        initializeReleaseWASDKeys(event);
        PlayerInputNewsPaper.INSTANCE.broadcast(buildSendInputStateToServerDTO());
    }

    private void initializeReleaseWASDKeys(KeyEvent event) {
        flipWASDKey(event, false);
    }
}
