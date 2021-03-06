package execution;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import config.Config;
import entities.Player;

public class Game extends ApplicationAdapter {
    public static TiledMapTileLayer collisionLayer;
    public Player player;
    public HUD hud;
    private SpriteBatch sb;
    private OrthographicCamera cam;
    private InputHandler inputHandler;
    private float delta;
    public Map map;
    public boolean debug;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT);
        sb = new SpriteBatch();
        hud = new HUD(this);
        inputHandler = new InputHandler(this);

        map = new Map(40, 25);
        player = new Player(this);
        player.setPosition(map.getStartingTile().getCenterX() - player.getWidth() / 2, map.getStartingTile().getCenterY() - player.getHeight() / 4);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update
        delta = Gdx.graphics.getDeltaTime();
        inputHandler.update();
        map.update(delta);
        player.update(delta);

        //update camera position
        sb.setProjectionMatrix(cam.combined);
        cam.position.x = player.getSprite().getX();
        cam.position.y = player.getSprite().getY();
        cam.update();

        //render
        sb.begin();
        map.render(sb);
        if (debug) map.debugRender(sb);
        Gdx.graphics.setTitle("" + Gdx.graphics.getFramesPerSecond());
        player.render(sb);
        sb.end();
        hud.render();
    }
}
