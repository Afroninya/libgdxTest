package execution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import config.Config;

public class Console {

    private boolean on;
    private TextField tf = new TextField("", new Skin(Gdx.files.internal("uiskin.json")));
    private Stage stage;
    private Game game;

    public Console(Game game) {
        this.game = game;
        int xOffset = 10;
        int yOffset = 40;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        tf.setPosition(xOffset, Config.VIEWPORT_HEIGHT - yOffset);
        tf.setSize(Config.VIEWPORT_WIDTH - 2 * xOffset, 30);
        stage.addActor(tf);
    }

    public void render() {
        if (!on) return;
        stage.draw();
        stage.act();
    }

    public void handleInput() {
        String text = tf.getText();
        String[] input = text.split("\\s+");
        switch (input[0].toLowerCase()) {
            //add console input cases here
            case "pos":
                break;
            case "position":
                break;
            case "teleport":
                break;
            case "tp":
                try {
                    game.player.setX(Integer.parseInt(input[1]));
                    game.player.setY(Integer.parseInt(input[2]));
                } catch (NumberFormatException ignored) {
                }
                break;
            case "debug":
                game.debug = !game.debug;
                break;
            case "combat":
                game.player.setInCombat(true);
                game.player.setInMovementPhase(true);
                break;
            case "endcombat":
                game.player.setInCombat(false);
                game.player.setInMovementPhase(false);
                break;
        }
        tf.setText("");
    }

    public void toggleConsole() {
        on = !on;
        if (on) {
            stage.setKeyboardFocus(tf);
        } else {
            stage.setKeyboardFocus(null);
        }
    }

    public boolean isOn() {
        return on;
    }
}
