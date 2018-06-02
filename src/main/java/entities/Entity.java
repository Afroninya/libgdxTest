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
    
    private final int COMBAT_MOVEMENT_SPEED = 300;

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

    protected double health;
    protected int damage;
    protected int movement;

    protected boolean isMoving;
    protected boolean isMovingUp;
    protected boolean isMovingDown;
    protected boolean isMovingRight;
    protected boolean isMovingLeft;

    protected boolean inCombat = false;
    protected boolean inMovementPhase = false;

    protected int combatTilesTraveledX;
    protected int combatTilesTraveledY;

    protected float destinationX;
    protected float destinationY;

    //0 up, 1 left, 2 down, 3 right
    protected Direction direction = Direction.DOWN;

    protected enum Direction {
        UP, LEFT, RIGHT, DOWN
    }

    protected TiledMapTileLayer collisionLayer;
    float stateTime;


    /**
     * Define an entity's movement behaviour by conditionally setting the direction attributes:
     * "isMoving", "isMovingUp", "isMovingDown", "isMovingRight", "isMovingLeft"
     */
    public abstract void handleMovement();
    
    public abstract void handleCombatMovement();

    public abstract void executeTurn();


    /**
     * Constructor.
     * Set attributes as specified in Subclass constructor call and initialize the Sprite.
     */
    public Entity(String spriteSheet, String initialSprite, short acceleration, short maxSpeed, double health, int damage,
                  int movement, float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.isMoving = false;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.health = health;
        this.damage = damage;
        this.movement = movement;
        this.width = width;
        this.height = height;

        initModel(x, y, spriteSheet, initialSprite, width, height);
    }


    /**
     * The engines update method. Called once every game tick / frame.
     *
     * @param delta the amount of time between frames in seconds.
     */
    public void update(float delta) {
        float oldX = x;
        float oldY = y;
        // update player movement
        
        if (!inCombat) {

            handleMovement();

            if (isMoving) {

                if (isMovingRight && !isMovingLeft) moveRight();
                if (isMovingLeft && !isMovingRight) moveLeft();
                if (isMovingUp && !isMovingDown) moveUp();
                if (isMovingDown && !isMovingUp) moveDown();

                if (speedX != 0 && (isMovingUp || isMovingDown)) inertia_x();
                else if (speedY != 0 && (isMovingRight || isMovingLeft)) inertia_y();

            } else phaseOutMovement();

        } else if (inMovementPhase) {
            speedY = 0;
            speedX = 0;
            
            handleCombatMovement();

            if (isMoving) {
                if (isMovingUp) moveTileUp(delta);
                if (isMovingDown) moveTileDown(delta);
                if (isMovingRight)  moveTileRight(delta);
                if (isMovingLeft) moveTileLeft(delta);
            }
        }

        move(delta);

        if (collides()) {
            setX(oldX);
            setY(oldY);
        }

        updateSprites();
    }

    // TODO: Replace damage with custom Class 'Attack' holding attack-information
    public void handleAttack(int damage) {
        this.health -= damage;
    }

    public void attack(Entity entity) {
        entity.handleAttack(this.damage);
    }

    public void attack(Tile[] tiles) {
        for (Tile tile : tiles) {
            tile.handleAttack(this.damage);
        }
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
        sb.draw(tr, x, y, width, height);
    }


    /**
     * Set an Entity's speed for it to move exactly one Tile up
     * @param delta delta
     */
    public void moveTileUp(float delta) {
        if (y < destinationY - COMBAT_MOVEMENT_SPEED * delta) speedY = COMBAT_MOVEMENT_SPEED;
        else {
            speedY = (destinationY - y) / delta;
            isMoving = false;
            isMovingUp = false;
        }
        direction = Direction.UP;
    }

    /**
     * Set an Entity's speed for it to move exactly one Tile right
     * @param delta delta
     */
    public void moveTileRight(float delta) {
        if (x < destinationX - COMBAT_MOVEMENT_SPEED * delta) speedX = COMBAT_MOVEMENT_SPEED;
        else {
            speedX = (destinationX - x) / delta;
            isMoving = false;
            isMovingRight = false;
        }
        direction = Direction.RIGHT;
    }

    /**
     * Set an Entity's speed for it to move exactly one Tile down
     * @param delta delta
     */
    public void moveTileDown(float delta) {
        if (y > destinationY + COMBAT_MOVEMENT_SPEED * delta) speedY = -COMBAT_MOVEMENT_SPEED;
        else {
            speedY = (destinationY - y) / delta;
            isMoving = false;
            isMovingDown = false;
        }
        direction = Direction.DOWN;
    }

    /**
     * Set an Entity's speed for it to move exactly one Tile left
     * @param delta delta
     */
    public void moveTileLeft(float delta) {
        if (x > destinationX + COMBAT_MOVEMENT_SPEED * delta) speedX = -COMBAT_MOVEMENT_SPEED;
        else {
            speedX = (destinationX - x) / delta;
            isMoving = false;
            isMovingLeft = false;
        }
        direction = Direction.LEFT;
    }

    /**
     * Accelerate in positive X direction and set the Sprite accordingly
     */
    public void moveRight() {
        speedX += (speedX < 0) ? acceleration * 2 : acceleration;
        if (speedX > maxSpeed) {
            speedX = maxSpeed;
        }
        direction = Direction.RIGHT;
    }

    /**
     * Accelerate in negative X direction and set the Sprite accordingly
     */
    public void moveLeft() {
        speedX -= (speedX > 0) ? acceleration * 2 : acceleration;
        if (speedX < -maxSpeed) {
            speedX = -maxSpeed;
        }
        direction = Direction.LEFT;
    }

    /**
     * Accelerate in positive Y direction and set the Sprite accordingly
     */
    public void moveUp() {
        speedY += (speedY < 0) ? acceleration * 2 : acceleration;
        if (speedY > maxSpeed) {
            speedY = maxSpeed;
        }
        direction = Direction.UP;
    }

    /**
     * Accelerate in negative Y direction and set the Sprite accordingly
     */
    public void moveDown() {
        speedY -= (speedY > 0) ? acceleration * 2 : acceleration;
        if (speedY < -maxSpeed) {
            speedY = -maxSpeed;
        }
        direction = Direction.DOWN;
    }

    /**
     * Set movement destination relative to the entity's current position.
     * @param x X destination
     * @param y Y destination
     */
    public void setDestination(float x, float y) {
        isMoving = true;
        destinationX = this.x + x;
        destinationY = this.y + y;

        if (y > 0) {
            isMovingUp = true;
            combatTilesTraveledY += 1;
        } else if (y < 0) {
            isMovingDown = true;
            combatTilesTraveledY -= 1;
        }
        if (x > 0) {
            isMovingRight = true;
            combatTilesTraveledX += 1;
        } else if (x < 0) {
            isMovingLeft = true;
            combatTilesTraveledX -= 1;
        }
    }


    /**
     * Check for any collision between entity and map.
     *
     * @return true if a collision occured
     */
    public boolean collides() {
        //Check For Collision
        boolean collisionWithMap = isCellBLocked(x, y);

        //React to Collision
        if (collisionWithMap) {
            Gdx.app.debug("Collision", "Player collides.");
        }
        return collisionWithMap;
    }

    /**
     * Check if the specified space is blocked by terrain.
     *
     * @param x X location
     * @param y Y location
     * @return true if space is blocked
     */
    public boolean isCellBLocked(float x, float y) {
        //TODO: collision
        return false;
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
     * Move with specified speed
     *
     * @param speedX     X speed
     * @param speedY     Y speed
     * @param delta frame based movement scaling
     */
    public void move(double speedX, double speedY, float delta) {
        double hypo = Math.hypot(speedX, speedY);
        double angle = (speedX == 0 && speedY == 0) ? 0 : Math.round(Math.toDegrees(Math.asin(Math.abs(speedX) / hypo)));

        double x_speedMod = angle / 90;
        double y_speedMod = 1 - (angle / 90);

        translateX((float) (speedX * x_speedMod) * delta);
        translateY((float) (speedY * y_speedMod) * delta);
    }

    /**
     * Move by specified amount and update the sprite accordingly
     *
     * @param x X amount
     */
    public void translateX(float x) {
        this.x += x;
        this.model.setX(this.x);
    }

    /**
     * Move by specified amount and update the sprite accordingly
     *
     * @param y Y amount
     */
    public void translateY(float y) {
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
     * Change the currently displayed sprite.
     *
     * @param sprite the suffix of the new sprite (e.g. "down")
     */
    public void setSprite(CommonSprites sprite) {
        this.model.setSprite(this.spriteBaseName + sprite.toString(), this.x, this.y);
    }

    public int getMovement() {
        return movement;
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

    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
}
