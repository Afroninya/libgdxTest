package execution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import java.util.ArrayList;

public class InputHandler {

    Game game;

    public InputHandler(Game game) {
        this.game = game;
    }

    public static ArrayList<Integer> keysPressed = new ArrayList<>();

    public void update() {
        // reset all pressed keys
        keysPressed.clear();

        //console handling
        if (Gdx.input.isKeyPressed(Keys.T) && !game.hud.getConsole().isOn()) {
            game.hud.getConsole().toggleConsole();
        }
        if (Gdx.input.isKeyPressed(Keys.ENTER) && game.hud.getConsole().isOn()) {
            game.hud.getConsole().handleInput();
            game.hud.getConsole().toggleConsole();
        }
        if (Gdx.input.isKeyPressed(Keys.ESCAPE) && game.hud.getConsole().isOn()) {
            game.hud.getConsole().toggleConsole();
        }
        if (game.hud.getConsole().isOn()) return;

        // add to list if key is touched
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            keysPressed.add(Keys.LEFT);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            keysPressed.add(Keys.DOWN);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            keysPressed.add(Keys.RIGHT);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            keysPressed.add(Keys.UP);
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            keysPressed.add(Keys.W);
        }
        if (Gdx.input.isKeyPressed(Keys.A)) {
            keysPressed.add(Keys.A);
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            keysPressed.add(Keys.S);
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            keysPressed.add(Keys.D);
        }
    }

    public static boolean isPressed(int key) {
        return keysPressed.contains(key);
    }
}
