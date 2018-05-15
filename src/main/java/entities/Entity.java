package entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import execution.Game;
import execution.InputHandler;
import graphics.Model;

abstract public class Entity {

    protected String spriteBaseName;
    protected String spriteSheet;
    protected Enum defaultSprite;
    protected Model model;

    protected float length;
    protected float width;

    protected float x;
    protected float y;
    protected float speed;
    protected float rotation;
    protected boolean isMoving;

    protected TiledMapTileLayer collisionLayer;


    private void initModel(float x, float y) {
        if (spriteSheet != null) {
            this.model = new Model(spriteSheet, defaultSprite, x, y);
        }
    }

    public void update(float delta) {
        float oldX = x;
        float oldY = y;
        // update player movement
        if (InputHandler.isPressed(Input.Keys.UP) || InputHandler.isPressed(Input.Keys.DOWN) ||
                InputHandler.isPressed(Input.Keys.LEFT) || InputHandler.isPressed(Input.Keys.RIGHT)) {
            isMoving = true;
        }
        if (isMoving) {

            // set rotation:
            if (InputHandler.isPressed(Input.Keys.UP) && !InputHandler.isPressed(Input.Keys.DOWN)) {
                rotation = 0;
                setSprite(spriteBaseName + "up");
            } else if (InputHandler.isPressed(Input.Keys.DOWN) && !InputHandler.isPressed(Input.Keys.UP)) {
                rotation = 180;
                setSprite(spriteBaseName + "down");
            } else {
                rotation = 90;
                setSprite(spriteBaseName + "middle");
            }

            float direction = rotation % 360;
            float speedMod = (direction % 90) / 89;

            if (direction < 90) {
                x += speed * speedMod * delta;
                y += speed * (1 - speedMod) * delta;
            }
            else if (direction < 180) {
                x += speed * (1 - speedMod) * delta;
                y -= speed * speedMod * delta;
            }
            else if (direction < 270) {
                x -= speed * speedMod * delta;
                y -= speed * (1 - speedMod) * delta;
            }
            else {
                x -= speed * (1 - speedMod) * delta;
                y += speed * speedMod * delta;
            }
            if (collides()) {
                x = oldX;
                y = oldY;
            }
            updateSprites();
        }
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


    public void updateSprites() {
        this.model.getSprites().values().forEach(x -> x.setPosition(this.x, this.y));
    }

    public <E extends Enum<E>> void setSprite(E sprite) {
        this.model.setSprite(sprite);
    }

    public void setSprite(String sprite) {
        this.model.setSprite(sprite);
    }

    public void translateX(float x) {
        this.x += x;
        this.model.setX(this.x + x);
    }

    public void translateY(float y) {
        this.y += y;
        this.model.setX(this.y + y);
    }


    public Entity(String spriteSheet, Enum defaultSprite, float x, float y) {
        this.rotation = 180;
        this.isMoving = false;
        this.spriteSheet = spriteSheet;
        this.defaultSprite = defaultSprite;
        this.spriteBaseName = defaultSprite.toString().split("_")[0] + "_";
        this.collisionLayer = Game.collisionLayer;
        initModel(x, y);
        setX(x);
        setY(y);
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
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
