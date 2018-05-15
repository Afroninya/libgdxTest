package entities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import config.Config;
import config.PathProvider;
import entities.util.PlayerSprites;
import execution.InputHandler;

public class Player extends LivingEntity {

    private static final String spriteSheet = PathProvider.SHIP + "ship.atlas";
    private static final Enum defaultSprite = PlayerSprites.SHIP_MIDDLE;
    private final float DEFAULT_SPEED = 60;

    public Player() {
        super(spriteSheet, defaultSprite, "player", Config.VIEWPORT_WIDTH / 2,Config.VIEWPORT_HEIGHT / 2);
        this.speed = DEFAULT_SPEED;

    }

//    public void update(float delta) {
//        float oldX = x;
//        float oldY = y;
//        // update player movement
//        if (InputHandler.isPressed(Keys.RIGHT)) {
//            translateX(speed * delta);
//        }
//        if (InputHandler.isPressed(Keys.LEFT)) {
//            translateX(-speed * delta);
//        }
//        if (InputHandler.isPressed(Keys.UP)) {
//            translateY(speed * delta);
//        }
//        if (InputHandler.isPressed(Keys.DOWN)) {
//            translateY(-speed * delta);
//        }
//
//        // set ship texture:
//        if (InputHandler.isPressed(Keys.UP) && !InputHandler.isPressed(Keys.DOWN)) {
//            setSprite(PlayerSprites.SHIP_UP);
//        } else if (InputHandler.isPressed(Keys.DOWN) && !InputHandler.isPressed(Keys.UP)) {
//            setSprite(PlayerSprites.SHIP_DOWN);
//        } else {
//            setSprite(PlayerSprites.SHIP_MIDDLE);
//        }
//        if (collides()) {
//            x = oldX;
//            y = oldY;
//        }
//        updateSprites();
//    }

    public void render(SpriteBatch sb) {
        model.getSprite().draw(sb);
    }
}