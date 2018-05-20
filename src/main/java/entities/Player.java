package entities;

import com.badlogic.gdx.Input;
import config.Config;
import entities.util.CommonSprites;
import execution.InputHandler;

public class Player extends LivingEntity {

    private static final String DEFAULT_SPRITE_SHEET = "player";
    private static final String DEFAULT_INITIAL_SPRITE = DEFAULT_SPRITE_SHEET + CommonSprites.DOWN;
    private final short DEFAULT_MAX_SPEED = 60;

    public Player() {
        this(DEFAULT_SPRITE_SHEET, DEFAULT_INITIAL_SPRITE, "player", (short) 100, (short) 5, (short) 100, Config.VIEWPORT_WIDTH / 2, Config.VIEWPORT_HEIGHT / 2);
    }

    public Player(String spriteSheet, String initialSprite, String name, short health, short acceleration, short maxSpeed, float x, float y) {
        super(spriteSheet, initialSprite, name, health, acceleration, maxSpeed, x, y);
    }


    public void handleMovement() {
        isMoving = (InputHandler.isPressed(Input.Keys.W) || InputHandler.isPressed(Input.Keys.A) ||
                InputHandler.isPressed(Input.Keys.S) || InputHandler.isPressed(Input.Keys.D));
        isMovingUp = (InputHandler.isPressed(Input.Keys.W) && !InputHandler.isPressed(Input.Keys.S));
        isMovingDown = (InputHandler.isPressed(Input.Keys.S) && !InputHandler.isPressed(Input.Keys.W));
        isMovingRight = (InputHandler.isPressed(Input.Keys.D) && !InputHandler.isPressed(Input.Keys.A));
        isMovingLeft = (InputHandler.isPressed(Input.Keys.A) && !InputHandler.isPressed(Input.Keys.D));
    }

}
