package execution;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import config.Config;
import config.PathProvider;
import entities.Player;

public class Game extends ApplicationAdapter {
    SpriteBatch sb;
    OrthographicCamera cam;
    Player player;
    private InputHandler inputHandler;
    private float delta;
    private TiledMap tileMap, collisionMap;
    private OrthogonalTiledMapRenderer tileMapRenderer, collisionMapRenderer;
    public static TiledMapTileLayer collisionLayer;

    @Override
    public void create() {
        cam = new OrthographicCamera();
        cam.setToOrtho(false, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT);
        sb = new SpriteBatch();

        //load tilemap
        tileMap = new TmxMapLoader().load(PathProvider.LEVEL1 + "level1.tmx");
        tileMapRenderer = new OrthogonalTiledMapRenderer(tileMap);


        //load collision map
        collisionMap = new TmxMapLoader().load(PathProvider.LEVEL1 + "level1_collision.tmx");
        collisionMapRenderer = new OrthogonalTiledMapRenderer(collisionMap);
        collisionLayer = (TiledMapTileLayer) collisionMap.getLayers().get(
                "Tile Layer 1");

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

        //update camera position
        sb.setProjectionMatrix(cam.combined);
        cam.position.x = player.getSprite().getX();
        cam.position.y = player.getSprite().getY();
        cam.update();

        //render
        tileMapRenderer.setView(cam);
        tileMapRenderer.render();
        collisionMapRenderer.setView(cam);
        collisionMapRenderer.render();
        sb.begin();
        player.render(sb);
        sb.end();
    }
}
