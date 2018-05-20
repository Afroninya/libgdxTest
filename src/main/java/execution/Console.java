package execution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import config.Config;

public class Console {

    private BitmapFont font;
    private boolean on;
    private TextField tf = new TextField("", new Skin(Gdx.files.internal("uiskin.json")));
    private Stage stage;
    private Game game;

    public Console(Game game) {
        this.game = game;
        int xOffset = 10;
        int yOffset = 60;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        tf.setPosition(xOffset, Config.V_HEIGHT - yOffset);
        tf.setSize(Config.V_WIDTH - 2 * xOffset, 30);
        stage.addActor(tf);
    }

    public void render(SpriteBatch sb) {
        if (!on) return;
        stage.draw();
        stage.act();
    }

    public void handleInput() {
        String text = tf.getText();
        String[] input = text.split("\\s+");
        switch (input[0].toLowerCase()) {
            //add console input cases here
            case "test":
                game.player.getSprite().rotate90(true);
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
