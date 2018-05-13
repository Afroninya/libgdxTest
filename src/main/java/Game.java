import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Game extends ApplicationAdapter {
    SpriteBatch sb;
    OrthographicCamera cam;
    Player player;
    private InputHandler inputHandler;
    private float delta;
    private TiledMap tileMap;
    private OrthogonalTiledMapRenderer tileMapRenderer;

    @Override
    public void create() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT);
        sb = new SpriteBatch();
        sb.setProjectionMatrix(cam.combined);
        //load tilemap
        tileMap = new TmxMapLoader().load("level1.tmx");
        tileMapRenderer = new OrthogonalTiledMapRenderer(tileMap);
        player = new Player();
        inputHandler = new InputHandler();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update
        delta = Gdx.graphics.getDeltaTime();
        inputHandler.update();
        player.update(delta);

        //render
        tileMapRenderer.setView(cam);
        tileMapRenderer.render();
        sb.begin();
        player.render(sb);
        sb.end();

        //update camera position
        cam.translate(10 * delta, 0, 0);
        cam.update();
    }
}
