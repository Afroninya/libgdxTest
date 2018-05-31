package entities;

import com.badlogic.gdx.Input;
import entities.util.CommonSprites;
import execution.InputHandler;

public class Player extends LivingEntity {

    private static final String DEFAULT_SPRITE_SHEET = "player";
    private static final String DEFAULT_INITIAL_SPRITE = DEFAULT_SPRITE_SHEET + CommonSprites.DOWN;
    private final short DEFAULT_MAX_SPEED = 60;

    public Player() {
        //350 and 100 are the start values for the player. don't knowsn
        this(DEFAULT_SPRITE_SHEET, DEFAULT_INITIAL_SPRITE, "player", (short) 100, (short) 15, (short) 300, 350, 150, 32, 48);
    }

    public Player(String spriteSheet, String initialSprite, String name, short health, short acceleration, short maxSpeed, float x, float y, int width, int height) {
        super(spriteSheet, initialSprite, name, health, acceleration, maxSpeed, x, y, width, height);
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
