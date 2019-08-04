package idema.michiel.game.engine.input;

import idema.michiel.game.spaceBalls.dto.SendInputStateToServerDTO;
import idema.michiel.newspaper.MessageType;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;

public class GameInput {

    private Canvas canvas;

    public GameInput(Canvas canvas){
        this.canvas = canvas;
    }

    public void initializeInput(){
        canvas.setOnKeyPressed(new KeyPressedEvent());
        canvas.setOnKeyReleased(new KeyReleasedEvent());
    }

    static void flipWASDKey(KeyEvent event, boolean b) {
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

    static SendInputStateToServerDTO buildSendInputStateToServerDTO() {
        return new SendInputStateToServerDTO(
                InputState.INSTANCE.getWKey(),
                InputState.INSTANCE.getAKey(),
                InputState.INSTANCE.getSKey(),
                InputState.INSTANCE.getDKey(),
                MessageType.SEND_INPUT_STATE_TO_SERVER.getValue());
    }
}
