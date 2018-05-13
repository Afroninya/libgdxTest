package graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import entities.Entity;

import java.util.HashMap;
import java.util.Map;

public class Model {

    private Entity entity;

    // current Position in Pixels
    private float x;
    private float y;

    private float width;
    private float height;

    // current Rotation in degrees
    private float rotation;

    // Sprite to be displayed
    private Sprite sprite;


    // Methods

    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    public Map<String, Integer> getCenter() {
        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        hmap.put("x", 1);
        hmap.put("y", 1);
        return hmap;
    }

    public void center() {
        setPosition(Gdx.graphics.getWidth()/2 - width + width/2,
                Gdx.graphics.getHeight()/2 - height + height/2);
    }

    public void rotate(float degrees) {
        setRotation(getRotation() + degrees);
    }


    // Constructors

    public Model() {
        x = 0;
        y = 0;
        rotation = 0;
    }

    public Model(String resourcePath) {
        new Model();
        sprite = new Sprite(new Texture(Gdx.files.internal(resourcePath)));
        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    public Model(String resourcePath, float x, float y) {
        new Model();
        sprite = new Sprite(new Texture(Gdx.files.internal(resourcePath)));
        setPosition(x, y);
    }

    public Model(String resourcePath, float x, float y, float rotation) {
        new Model();
        sprite = new Sprite(new Texture(Gdx.files.internal(resourcePath)));
        setPosition(x, y);
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
        return sprite;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


    // Setter

    public void setX(float x) {
        this.x = x;
        sprite.setX(x);
    }

    public void setY(float y) {
        this.y = y;
        sprite.setY(y);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        sprite.setRotation(rotation);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
