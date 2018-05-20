package execution;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HUD {
    private SpriteBatch sb;
    private Console console;
    private Game game;

    public HUD(Game game) {
        this.game = game;
        sb = new SpriteBatch();
        console = new Console(game);
    }

    public void render() {
        sb.begin();
        console.render();
        sb.end();
    }

    public Console getConsole() {
        return console;
    }
}
