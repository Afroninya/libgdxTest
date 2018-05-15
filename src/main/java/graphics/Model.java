package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

public class Model {

    // current Position relative to the display in Pixels
    private float x;
    private float y;

    private float width;
    private float height;

    // current Rotation in degrees
    private float rotation;

    // Sprite to be displayed
    private Sprite currentSprite;
    private TextureAtlas textureAtlas;

    private Map<String, Sprite> sprites;


    // Methods

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public float getCenterX() {
        return getCenter().get("x");
    }

    public float getCenterY() {
        return getCenter().get("y");
    }

    public Map<String, Float> getCenter() {
        HashMap<String, Float> hmap = new HashMap<String, Float>();
        hmap.put("x", x + width/2);
        hmap.put("y", y + height/2);
        return hmap;
    }

    public void centerOnScreen() {
        setPosition(Gdx.graphics.getWidth()/2 - width + width/2,
                Gdx.graphics.getHeight()/2 - height + height/2);
    }

    public void rotate(float degrees) {
        rotation += degrees;
    }

    private void initTextures(String spriteSheet, Enum defaultSprite, float x, float y) {
        if (spriteSheet != null && spriteSheet.endsWith(".atlas")) {
            sprites = new HashMap<String, Sprite>();
            this.textureAtlas = new TextureAtlas(Gdx.files.internal(spriteSheet));

            textureAtlas.getRegions().forEach(element -> {
                Sprite tmpSprite = new Sprite(element);
                tmpSprite.setPosition(x, y);
                sprites.put(element.name, tmpSprite);
            });
            setSprite(defaultSprite);
        }
    }

    public <E extends Enum<E>> void setSprite(E sprite) {
        this.currentSprite = sprites.get(sprite.toString());
    }

    public void setSprite(String sprite) {
        this.currentSprite = sprites.get(sprite);
    }


    // Constructors

    public Model() {
        this(null, null, 0, 0, 0);
    }

    public Model(String spriteSheet, Enum defaultSprite) {
        this(spriteSheet, defaultSprite, 0, 0, 0);
    }

    public Model(String spriteSheet, Enum defaultSprite, float x, float y) {
        this(spriteSheet, defaultSprite, x, y, 0);
    }

    public Model(String spriteSheet, Enum defaultSprite, float x, float y, float rotation) {
        initTextures(spriteSheet, defaultSprite, x, y);
        setRotation(rotation);
    }


    // Getter

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
