import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import config.Config;
import execution.Game;

import java.util.ArrayList;

public class Menu {

    private boolean open;
    private ArrayList<String> options;
    private TextField tf = new TextField("", new Skin(Gdx.files.internal("uiskin.json")));
    private Stage stage;
    private Game game;

    public Menu(Game game) {
        this.game = game;
        int xOffset = 10;
        int yOffset = 60;
        stage = new Stage();
        options.add("Inventory");
        options.add("Save and Quit");
        Gdx.input.setInputProcessor(stage);
        tf.setPosition(xOffset, Config.V_HEIGHT - yOffset);
        tf.setSize(Config.V_WIDTH - 2 * xOffset, 30);
        stage.addActor(tf);
    }

    public void render(SpriteBatch sb) {
        if (!open) return;
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
        open = !open;
        if (open) {
            stage.setKeyboardFocus(tf);
        } else {
            stage.setKeyboardFocus(null);
        }
    }
}
