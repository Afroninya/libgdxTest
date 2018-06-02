import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import config.Config;
import execution.Game;

public class Main {
    public static void main(String[] args) {
        Config.getConfig();
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "test";
        cfg.width = Config.VIEWPORT_WIDTH;
        cfg.height = Config.VIEWPORT_HEIGHT;
        cfg.forceExit = false;
        //test
        new LwjglApplication(new Game(), cfg);
    }
}