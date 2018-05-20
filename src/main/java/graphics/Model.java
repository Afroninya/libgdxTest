package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class Model {

    // current Position relative to the display in Pixels
    private float x;
    private float y;

    //TODO: assign
    private float width;
    private float height;

    // current Rotation in degrees
    private float rotation;

    // Sprite to be displayed
    private Sprite currentSprite;
    private TextureAtlas textureAtlas;

    private Map<String, Sprite> sprites;
    public Map<String, Animation<TextureRegion>> animations;


    // Constructors

    public Model() {
        this(null, null);
    }

    public Model(String spriteSheet, String defaultSprite) {
        this(spriteSheet, defaultSprite, 0, 0);
    }

    public Model(String spriteSheet, String defaultSprite, float x, float y) {
        this(spriteSheet, defaultSprite, x, y, 0, 16, 24);
    }

    public Model(String spriteSheet, String defaultSprite, float x, float y, float rotation, int width, int height) {
        this.x = x;
        this.y = y;
        initTextures(spriteSheet, defaultSprite, x, y, width, height);
//        setRotation(rotation);
    }

    // Methods

    /**
     * Load spritesheet and save sprites into a TextureAtlas
     *
     * @param spriteSheet   spritesheet base name
     * @param defaultSprite initially displayed sprite
     * @param x             X location
     * @param y             Y location
     */
    private void initTextures(String spriteSheet, String defaultSprite, float x, float y, int width, int height) {
        if (spriteSheet != null && spriteSheet.endsWith(".atlas")) {
            sprites = new HashMap<String, Sprite>();
            animations = new HashMap<String, Animation<TextureRegion>>();
            this.textureAtlas = new TextureAtlas(Gdx.files.internal(spriteSheet));

            textureAtlas.getRegions().forEach(element -> {
                Sprite tmpSprite = new Sprite(element);
                tmpSprite.setPosition(x, y);
                sprites.put(element.name, tmpSprite);
            });

            animations.put("down", new Animation<TextureRegion>(0.2f, textureAtlas.findRegions("player_down"), Animation.PlayMode.LOOP));
            animations.put("up", new Animation<TextureRegion>(0.2f, textureAtlas.findRegions("player_up"), Animation.PlayMode.LOOP));
            animations.put("left", new Animation<TextureRegion>(0.2f, textureAtlas.findRegions("player_left"), Animation.PlayMode.LOOP));
            animations.put("right", new Animation<TextureRegion>(0.2f, textureAtlas.findRegions("player_right"), Animation.PlayMode.LOOP));
            setSprite(defaultSprite);
        }
    }

    /**
     * Center the model on the current screen.
     */
    public void centerOnScreen() {
        setPosition(Gdx.graphics.getWidth() / 2 - width + width / 2,
                Gdx.graphics.getHeight() / 2 - height + height / 2);
    }

    /**
     * Rotate the model by specified amount.
     *
     * @param degrees rotation amount in degrees
     */
    public void rotate(float degrees) {
        rotation += degrees;
    }


    // Getter

    public Map<String, Float> getCenter() {
        HashMap<String, Float> hmap = new HashMap<String, Float>();
        hmap.put("x", x + width / 2);
        hmap.put("y", y + height / 2);
        return hmap;
    }

    public float getCenterX() {
        return getCenter().get("x");
    }

    public float getCenterY() {
        return getCenter().get("y");
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRotation() {
        return rotation;
    }

    public Sprite getSprite() {
        return currentSprite;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Map<String, Sprite> getSprites() {
        return sprites;
    }

    // Setter

    public void setSprite(String sprite) {
        this.currentSprite = sprites.get(sprite);
    }

    public void setSprite(String sprite, float x, float y) {
        this.currentSprite = sprites.get(sprite);
        setPosition(x, y);
    }

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public void setX(float x) {
        this.x = x;
        currentSprite.setX(x);
    }

    public void setY(float y) {
        this.y = y;
        currentSprite.setY(y);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        currentSprite.setRotation(rotation);
    }
}
