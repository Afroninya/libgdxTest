package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import config.ConfigValueProvider;
import entities.util.CommonSprites;
import execution.Game;
import execution.Tile;
import graphics.Model;

import java.util.MissingResourceException;

abstract public class Entity {

    protected Game game;

    protected String spriteBaseName;
    protected String spriteSheet;
    protected String defaultSprite;
    protected Model model;

    protected float width;
    protected float height;

    protected float x;
    protected float y;
    protected double speedX;
    protected double speedY;
    protected double maxSpeed;
    protected double acceleration;

    protected boolean phaseMovement;

    protected boolean isMoving;
    protected boolean isMovingUp;
    protected boolean isMovingDown;
    protected boolean isMovingRight;
    protected boolean isMovingLeft;

    protected boolean inCombat = false;

    //0 up, 1 left, 2 down, 3 right
    private Direction direction = Direction.DOWN;

    private enum Direction {
        UP, LEFT, RIGHT, DOWN
    }

    protected TiledMapTileLayer collisionLayer;
    float stateTime;


    /**
     * Define an entity's movement behaviour by conditionally setting the direction attributes:
     * "isMoving", "isMovingUp", "isMovingDown", "isMovingRight", "isMovingLeft"
     */
    public abstract void handleMovement();


    /**
     * Constructor.
     * Set attributes as specified in Subclass constructor call and initialize the Sprite.
     */
    public Entity(Game game, String spriteSheet, String initialSprite, short acceleration, short maxSpeed, float x, float y, int width, int height) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.isMoving = false;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.width = width;
        this.height = height;

        initModel(x, y, spriteSheet, initialSprite, width, height);
    }

    /**
     * Initialize the Sprite
     *
     * @param x             initial X position
     * @param y             initial Y position
     * @param spriteSheet   base name of the spritesheet file (e.g. "player.atlas")
     * @param initialSprite the texture file name (e.g. "player_down")
     */
    private void initModel(float x, float y, String spriteSheet, String initialSprite, int width, int height) {
        if (spriteSheet == null) {
            final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String className = stackTrace[3].getClassName().toLowerCase().split("\\.")[1];

            try {
                this.spriteSheet = ConfigValueProvider.getSpritesLocation(className);
                this.spriteBaseName = className;
                this.defaultSprite = className + "_middle";
            } catch (MissingResourceException e) {
                e.printStackTrace();
                System.out.println("No Spritesheet found for Entity " + className);
                throw (e);
            }
        } else {
            if (initialSprite == null || !initialSprite.contains(spriteSheet)) {
                this.defaultSprite = spriteSheet + CommonSprites.DOWN;
            } else {
                this.defaultSprite = initialSprite;
            }
            this.spriteBaseName = spriteSheet;
            this.spriteSheet = ConfigValueProvider.getSpritesLocation(spriteSheet);
        }

        this.collisionLayer = Game.collisionLayer;

        this.model = new Model(this.spriteSheet, defaultSprite, x, y, 0, width, height);
    }


    /**
     * The engines update method. Called once every game tick / frame.
     *
     * @param delta the amount of time between frames in seconds.
     */
    public void update(float delta) {
        // update player movement
        handleMovement();

        if (!inCombat) {

            if (isMoving) {
                if (isMovingRight && !isMovingLeft) {
                    moveRight();
                    direction = Direction.RIGHT;
                }
                if (isMovingLeft && !isMovingRight) {
                    moveLeft();
                    direction = Direction.LEFT;
                }
                if (isMovingUp && !isMovingDown) {
                    moveUp();
                    direction = Direction.UP;
                }
                if (isMovingDown && !isMovingUp) {
                    moveDown();
                    direction = Direction.DOWN;
                }

                if (speedX != 0 && (isMovingUp || isMovingDown)) {
                    inertia_x();
                } else if (speedY != 0 && (isMovingRight || isMovingLeft)) {
                    inertia_y();
                }
                move(delta);
                updateSprites();
            } else {
                phaseOutMovement();
            }
        }

    }

    /**
     * The engines render method. Called once every game tick
     *
     * @param sb the corresponding SpriteBatch
     */
    public void render(SpriteBatch sb) {
        stateTime += Gdx.graphics.getDeltaTime();

        // Get current frame of animation for the current stateTime
        TextureRegion tr;
        if (isMovingLeft) tr = model.animations.get("left").getKeyFrame(stateTime, true);
        else if (isMovingRight) tr = model.animations.get("right").getKeyFrame(stateTime, true);
        else if (isMovingUp) tr = model.animations.get("up").getKeyFrame(stateTime, true);
        else if (isMovingDown) tr = model.animations.get("down").getKeyFrame(stateTime, true);
        else tr = model.animations.get(direction.name().toLowerCase()).getKeyFrame(2);
        sb.draw(tr, x, y, width, height);
    }


    /**
     * Accelerate in positive X direction and set the Sprite accordingly
     */
    public void moveRight() {
        speedX += (speedX < 0) ? acceleration * 2 : acceleration;
        if (speedX > maxSpeed) {
            speedX = maxSpeed;
        }
        setSprite(CommonSprites.RIGHT);
    }

    /**
     * Accelerate in negative X direction and set the Sprite accordingly
     */
    public void moveLeft() {
        speedX -= (speedX > 0) ? acceleration * 2 : acceleration;
        if (speedX < -maxSpeed) {
            speedX = -maxSpeed;
        }
        setSprite(CommonSprites.LEFT);
    }

    /**
     * Accelerate in positive Y direction and set the Sprite accordingly
     */
    public void moveUp() {
        speedY += (speedY < 0) ? acceleration * 2 : acceleration;
        if (speedY > maxSpeed) {
            speedY = maxSpeed;
        }
        setSprite(CommonSprites.UP);
    }

    /**
     * Accelerate in negative Y direction and set the Sprite accordingly
     */
    public void moveDown() {
        speedY -= (speedY > 0) ? acceleration * 2 : acceleration;
        if (speedY < -maxSpeed) {
            speedY = -maxSpeed;
        }
        setSprite(CommonSprites.DOWN);
    }


    /**
     * Check for any collision between entity and map.
     *
     * @return true if a collision occured
     */
    public boolean collides(int x, int y) {
        //Check For Collision
        // bottom left corner
        Tile tile = game.map.getTilePixel((int) x, (int) y);
        // top right corner
        Tile tile2 = game.map.getTilePixel((int) (x + width), (int) (y + width));
        // top left corner
        Tile tile3 = game.map.getTilePixel((int) (x), (int) (y + width));
        // bottom right corner
        Tile tile4 = game.map.getTilePixel((int) (x + width), (int) (y));
        try {
            boolean collisionWithMap = (tile == null) || (!tile.isPassable() || !tile3.isPassable() || !tile2.isPassable() || !tile4.isPassable());
            //React to Collision
            if (collisionWithMap) {
                if (speedX != 0 && speedY != 0) {
                    if ((!tile.isPassable() && !tile3.isPassable()) || (!tile2.isPassable() && !tile4.isPassable()))
                        speedX = 0;
                    if ((!tile.isPassable() && !tile4.isPassable()) || (!tile2.isPassable() && !tile3.isPassable()))
                        speedY = 0;
                }
                Gdx.app.debug("Collision", "Player collides.");
            }
            return collisionWithMap;
        } catch (NullPointerException e) {
            //in case any of the tiles is outside of the map
            return true;
        }
    }

    /**
     * Converge speed to 0 if entity is not moving.
     */
    public void phaseOutMovement() {
        inertia_y();
        inertia_x();
    }

    /**
     * Converge X speed to 0 if entity is not moving in any X direction.
     */
    public void inertia_x() {
        if (!isMovingLeft && !isMovingRight) {
            if (Math.abs(speedX) < acceleration) speedX = 0;
            else if (speedX < 0) speedX += acceleration;
            else speedX -= acceleration;
        }
    }

    /**
     * Converge Y speed to 0 if entity is not moving in any Y direction.
     */
    public void inertia_y() {
        if (!isMovingUp && !isMovingDown) {
            if (Math.abs(speedY) < acceleration) speedY = 0;
            else if (speedY < 0) speedY += acceleration;
            else speedY -= acceleration;
        }
    }

    /**
     * Move based on the entity's speed.
     *
     * @param delta frame based movement scaling
     */
    public void move(float delta) {
        move(speedX, speedY, delta);
    }

    /**
     * Move by a specified amount
     *
     * @param x     X amount
     * @param y     Y amount
     * @param delta frame based movement scaling
     */
    public void move(double x, double y, float delta) {
        double hypo = Math.hypot(x, y);
        double angle = (x == 0 && y == 0) ? 0 : Math.round(Math.toDegrees(Math.asin(Math.abs(x) / hypo)));

        double x_speedMod = angle / 90;
        double y_speedMod = 1 - (angle / 90);

        float xTranslate = (float) (x * x_speedMod) * delta;
        float yTranslate = (float) (y * y_speedMod) * delta;
        translateX(xTranslate);
        translateY(yTranslate);
    }

    /**
     * Move by specified amount and update the sprite accordingly
     *
     * @param x X amount
     */
    public void translateX(float x) {
        if (collides((int) (this.x + x), (int) y)) return;
        this.x += x;
        this.model.setX(this.x);
    }

    /**
     * Move by specified amount and update the sprite accordingly
     *
     * @param y Y amount
     */
    public void translateY(float y) {
        if (collides((int) x, (int) (this.y + y))) return;
        this.y += y;
        this.model.setY(this.y);
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    /**
     * Update location for all sprites.
     */
    public void updateSprites() {
        this.model.getSprites().values().forEach(x -> x.setPosition(this.x, this.y));
    }

    /**
     * Change the currently displayed sprite.
     *
     * @param sprite the suffix of the new sprite (e.g. "down")
     */
    public void setSprite(CommonSprites sprite) {
        this.model.setSprite(this.spriteBaseName + sprite.toString(), this.x, this.y);
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public Sprite getSprite() {
        return model.getSprite();
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        this.model.setX(x);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        this.model.setY(y);
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
}
