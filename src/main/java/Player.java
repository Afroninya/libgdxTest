import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {

    private float x, y;
    private Texture ship, ship_up, ship_middle, ship_down;
    private float speed = 60;

    public Player() {
        x = Config.VIEWPORT_WIDTH / 2;
        y = Config.VIEWPORT_HEIGHT / 2;

        loadPlayerTextures();
    }

    public void update(float delta) {
// update player movement
        if (InputHandler.RIGHT_TOUCHED) {
            x += speed * delta;
        }
        if (InputHandler.LEFT_TOUCHED) {
            x -= speed * delta;
        }
        if (InputHandler.UP_TOUCHED) {
            y += speed * delta;
        }
        if (InputHandler.DOWN_TOUCHED) {
            y -= speed * delta;
        }

        // set ship texture:
        if (InputHandler.UP_TOUCHED == true && InputHandler.DOWN_TOUCHED == false) {
            ship = ship_up;
        } else if (InputHandler.DOWN_TOUCHED == true && InputHandler.UP_TOUCHED == false) {
            ship = ship_down;
        } else {
            ship = ship_middle;
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}