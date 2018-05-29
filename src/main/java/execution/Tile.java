package execution;

import com.badlogic.gdx.graphics.Texture;
import entities.Entity;

import java.util.ArrayList;

public class Tile {
    public static final int WIDTH = 32;
    //the x and y coordinates of the tile (not in pixels, but tiles)
    private int tileX;
    private int tileY;
    //the tiles texture
    private Texture texture;
    private boolean passable;
    //the entities that currently reside on the tile
    public ArrayList<Entity> entities;

    //CONSTRUCTOR
    public Tile(int tileX, int tileY, boolean passable) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.passable = passable;
        //TODO: assign texture
        entities = new ArrayList<>();
    }

    public void draw() {
        //TODO: calculate pixelX and pixelY
        //TODO: draw texture at pixelX, pixelY with size WIDTH*WIDTH
    }

    public void update(float delta) {
        for (Entity e : entities) {
            e.update(delta);
        }
    }

    public String toString() {
        return "Tile {" + tileX + " | " + tileY + ", passable=" + passable + "}";
    }
}
