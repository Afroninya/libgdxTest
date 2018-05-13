import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "test";
        cfg.width = Config.V_WIDTH;
        cfg.height = Config.V_HEIGHT;
        cfg.forceExit = false;
        //test
        new LwjglApplication(new Game(), cfg);
    }
}
