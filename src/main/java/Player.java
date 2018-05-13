import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player {

    private float x, y;
    private Texture ship, ship_up, ship_middle, ship_down;
    private float speed = 60;
    private boolean isMoving = false;
    private TiledMapTileLayer collisionLayer;

    public Player(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
        x = Config.VIEWPORT_WIDTH / 2;
        y = Config.VIEWPORT_HEIGHT / 2;

        loadPlayerTextures();
    }

    public void update(float delta) {
        float oldX = x;
        float oldY = y;
        // update player movement
        if (InputHandler.isPressed(Keys.RIGHT)) {
            x += speed * delta;
        }
        if (InputHandler.isPressed(Keys.LEFT)) {
            x -= speed * delta;
        }
        if (InputHandler.isPressed(Keys.UP)) {
            y += speed * delta;
        }
        if (InputHandler.isPressed(Keys.DOWN)) {
            y -= speed * delta;
        }

        // set ship texture:
        if (InputHandler.isPressed(Keys.UP) && !InputHandler.isPressed(Keys.DOWN)) {
            ship = ship_up;
        } else if (InputHandler.isPressed(Keys.DOWN) && !InputHandler.isPressed(Keys.UP)) {
            ship = ship_down;
        } else {
            ship = ship_middle;
        }
        if (collides()) {
            x = oldX;
            y = oldY;
        }
    }

    public void render(SpriteBatch sb) {
        sb.draw(ship, x, y);
    }

    public void loadPlayerTextures() {
        ship_middle = new Texture("ship_middle.png");
        ship_up = new Texture("ship_up.png");
        ship_down = new Texture("ship_down.png");
    }

    public boolean collides(){
        //Check For Collision
        boolean collisionWithMap = false;
        // check right side middle
        collisionWithMap = isCellBLocked(x, y);

        //React to Collision
        if (collisionWithMap) {
            System.out.println("player-map collision!!!");
            return true;
        }
        return false;
    }

    public boolean isCellBLocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(
                (int) (x / collisionLayer.getTileWidth()),
                (int) (y / collisionLayer.getTileHeight()));

        return cell != null && cell.getTile() != null
                && cell.getTile().getProperties().containsKey("blocked");
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}