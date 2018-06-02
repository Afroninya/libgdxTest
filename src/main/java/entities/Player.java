package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import entities.util.CommonSprites;
import execution.Game;
import execution.InputHandler;
import execution.Tile;

public class Player extends LivingEntity {

    private static final String DEFAULT_SPRITE_SHEET = "player";
    private static final String DEFAULT_INITIAL_SPRITE = DEFAULT_SPRITE_SHEET + CommonSprites.DOWN;
    private final short DEFAULT_MAX_SPEED = 60;

    private float movementOriginX = -1;
    private float movementOriginY;

    public Player(Game game) {
        //350 and 100 are the start values for the player. don't knowsn
        this(game, DEFAULT_SPRITE_SHEET, DEFAULT_INITIAL_SPRITE, "player", (short) 100, (short) 15, (short) 300, 10, 5, 350, 150, 32, 48);
    }

    public Player(Game game, String spriteSheet, String initialSprite, String name, short health, short acceleration, short maxSpeed, int damage,
                  int movement, float x, float y, int width, int height) {
        super(game, spriteSheet, initialSprite, name, health, acceleration, maxSpeed, damage, movement, x, y, width, height);
    }


    public void handleMovement() {
        isMoving = (InputHandler.isPressed(Input.Keys.W) || InputHandler.isPressed(Input.Keys.A) ||
                InputHandler.isPressed(Input.Keys.S) || InputHandler.isPressed(Input.Keys.D));
        isMovingUp = (InputHandler.isPressed(Input.Keys.W) && !InputHandler.isPressed(Input.Keys.S));
        isMovingDown = (InputHandler.isPressed(Input.Keys.S) && !InputHandler.isPressed(Input.Keys.W));
        isMovingRight = (InputHandler.isPressed(Input.Keys.D) && !InputHandler.isPressed(Input.Keys.A));
        isMovingLeft = (InputHandler.isPressed(Input.Keys.A) && !InputHandler.isPressed(Input.Keys.D));
    }

    public void handleCombatMovement() {

        // Move Tile Up
        if (!isMoving && (combatTilesTraveledY + Math.abs(combatTilesTraveledX) != movement) && (InputHandler.keyUp == Input.Keys.W && !(InputHandler.keyUp == Input.Keys.S))) {
            setDestination(0, Tile.WIDTH);
        }

        // Move Tile Right
        if (!isMoving && (Math.abs(combatTilesTraveledY) + combatTilesTraveledX != movement) && (InputHandler.keyUp == Input.Keys.D && !(InputHandler.keyUp == Input.Keys.A))) {
            setDestination(Tile.WIDTH, 0);
        }

        // Move Tile Down
        if (!isMoving && (combatTilesTraveledY - Math.abs(combatTilesTraveledX) != -movement) && (InputHandler.keyUp == Input.Keys.S && !(InputHandler.keyUp == Input.Keys.W))) {
             setDestination(0, -Tile.WIDTH);
        }

        // Move Tile Left
        if (!isMoving && (combatTilesTraveledX - Math.abs(combatTilesTraveledY) != -movement) && (InputHandler.keyUp == Input.Keys.A && !(InputHandler.keyUp == Input.Keys.D))) {
            setDestination(-Tile.WIDTH, 0);
        }
    }

    public void executeTurn() {
    }


    /**
     * The engines render method. Called once every game tick
     *
     * @param sb the corresponding SpriteBatch
     */
    public void render(SpriteBatch sb) {
        stateTime += Gdx.graphics.getDeltaTime();
        executeTurn();
        // Get current frame of animation for the current stateTime
        TextureRegion tr;
        if (isMovingLeft) tr = model.animations.get("left").getKeyFrame(stateTime, true);
        else if (isMovingRight) tr = model.animations.get("right").getKeyFrame(stateTime, true);
        else if (isMovingUp) tr = model.animations.get("up").getKeyFrame(stateTime, true);
        else if (isMovingDown) tr = model.animations.get("down").getKeyFrame(stateTime, true);
        else tr = model.animations.get(direction.name().toLowerCase()).getKeyFrame(2);
        if (inMovementPhase) {
            drawMovementRestriction(sb);
        }
        sb.draw(tr, x, y, width, height);
    }


    /**
     * Draw a controllable character's movement restriction during combat
     * @param sb SpriteBatch
     */
    public void drawMovementRestriction(SpriteBatch sb) {

        if (movementOriginX == -1) {
            movementOriginX = x;
            movementOriginY = y;
        }

        float textureOriginX = movementOriginX + width / 2 - Tile.WIDTH / 2;
        float textureOriginY = movementOriginY + height / 4 - Tile.WIDTH / 2;

        Texture texture = getMovementRestriction();

        for (int i = 1; i <= movement; i++) {

            sb.draw(texture, textureOriginX + i * Tile.WIDTH, textureOriginY);
            sb.draw(texture, textureOriginX - i * Tile.WIDTH, textureOriginY);

            sb.draw(texture, textureOriginX, textureOriginY + i * Tile.WIDTH);
            sb.draw(texture, textureOriginX, textureOriginY - i * Tile.WIDTH);

            for (int y = 1; y <= movement - i; y++) {

                sb.draw(texture, textureOriginX + i * Tile.WIDTH, textureOriginY + y * Tile.WIDTH);
                sb.draw(texture, textureOriginX + i * Tile.WIDTH, textureOriginY - y * Tile.WIDTH);

                sb.draw(texture, textureOriginX - i * Tile.WIDTH, textureOriginY + y * Tile.WIDTH);
                sb.draw(texture, textureOriginX - i * Tile.WIDTH, textureOriginY - y * Tile.WIDTH);
            }
        }
    }

    /**
     * Draw a semi-transparent rectangle close to the size of a Tile
     * @return
     */
    public Texture getMovementRestriction() {
        // show accessible tiles
        Pixmap pixmap = new Pixmap(Tile.WIDTH - 4,Tile.WIDTH - 4, Pixmap.Format.RGBA8888);

        pixmap.setColor(0, 255, 0, 0.6f);
        pixmap.fillRectangle(2, 2, Tile.WIDTH - 8, Tile.WIDTH - 8);
        pixmap.setColor(0, 255, 0, 0.8f);
        pixmap.drawRectangle(2, 2, Tile.WIDTH - 8, Tile.WIDTH - 8);

        return new Texture(pixmap);
    }
}
