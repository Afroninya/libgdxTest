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

    enum TileType {
        GRASS, STONE, TRUMP;
    }

    //CONSTRUCTOR
    public Tile(int tileX, int tileY, boolean passable) {
        this.tileX = tileX;
        this.tileY = tileY;
        this.passable = passable;
        this.texture = new Texture("textures/trump.jpg");
        //TODO: assign texture
        entities = new ArrayList<>();
        this.font = new BitmapFont();
    }

    public void draw(SpriteBatch sb) {
        font.setColor(Color.YELLOW);
        sb.draw(texture, tileX*WIDTH, tileY*WIDTH);
        font.draw(sb, tileX + ", " + tileY, tileX*WIDTH, tileY*WIDTH);
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
