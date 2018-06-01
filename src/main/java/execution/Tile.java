package execution;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import entities.Entity;

import java.util.ArrayList;

public class Tile {
    public static final int WIDTH = 64;
    //the x and y coordinates of the tile (not in pixels, but tiles)
    private int tileX;
    private int tileY;
    //the tiles texture
    private Texture texture;
    private boolean passable;
    //the entities that currently reside on the tile
    public ArrayList<Entity> entities;
    private BitmapFont font;
    private TileType type;

    enum TileType {
        GRASS, STONE
    }

    //CONSTRUCTOR
    public Tile(int tileX, int tileY, TileType type) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.type = type;
        switch (type) {
            case GRASS:
                this.passable = true;
                this.texture = new Texture("textures/grass.png");
                break;
            case STONE:
                this.passable = false;
                this.texture = new Texture("textures/stone.png");
                break;
        }
        entities = new ArrayList<>();
        this.font = new BitmapFont();
    }

    public void draw(SpriteBatch sb) {
        sb.draw(texture, tileX * WIDTH, tileY * WIDTH, WIDTH, WIDTH);
    }

    public void debugDraw(SpriteBatch sb) {
        font.setColor(Color.WHITE);
        font.draw(sb, tileX + ", " + tileY, tileX * WIDTH, tileY * WIDTH + WIDTH);
    }

    public void update(float delta) {
        for (Entity e : entities) {
            e.update(delta);
        }
    }

    public int getOriginX() {
        return tileX * WIDTH;
    }

    public int getOriginY() {
        return tileY * WIDTH;
    }

    public float getCenterX() {
        return (tileX * WIDTH) + (WIDTH / 2);
    }

    public float getCenterY() {
        return (tileY * WIDTH) + (WIDTH / 2);
    }

    public String toString() {
        return "Tile {" + tileX + " | " + tileY + ", passable=" + passable + "}";
    }

    public boolean isPassable() {
        return passable;
    }
}
