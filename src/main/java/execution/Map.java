package execution;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    //the tilemap
    public ArrayList<Tile> tiles;
    //the width of the map in tiles
    private int numberOfTilesX;
    //the height of the map in tiles
    private int numberOfTilesY;
    private Tile startingTile;

    //CONSTRUCTOR
    public Map(int numberOfTilesX, int numberOfTilesY) {
        tiles = new ArrayList<>();
        this.numberOfTilesX = numberOfTilesX;
        this.numberOfTilesY = numberOfTilesY;
        //fill map with x*y tiles, assign unique coordinates to each of them
        for (int x = 0; x < numberOfTilesX; x++) {
            for (int y = 0; y < numberOfTilesY; y++) {
                if (Math.random() > 0.2) {
                    tiles.add(new Tile(x, y, Tile.TileType.GRASS));
                } else {
                    tiles.add(new Tile(x, y, Tile.TileType.STONE));
                }
            }
        }
        startingTile = getRandomUnoccupiedTile();
    }

    public Tile getStartingTile() {
        return startingTile;
    }

    public void update(float delta) {
        tiles.forEach(tile -> tile.update(delta));
    }

    public void render(SpriteBatch sb) {
        Texture tex = new Texture("textures/stone.png");
        tex.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        sb.draw(tex, -5*Tile.WIDTH, -5*Tile.WIDTH, 0, 0, (10+numberOfTilesX) * Tile.WIDTH, (10+numberOfTilesY) * Tile.WIDTH);
        tiles.forEach(tile -> tile.draw(sb));
    }

    public void debugRender(SpriteBatch sb) {
        tiles.forEach(tile -> tile.debugDraw(sb));
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x > numberOfTilesX - 1 || y > numberOfTilesY - 1) return null;
        try {
            return tiles.get(y + numberOfTilesY * x);
        } catch (Exception e) {
            return null;
        }
    }

    public Tile getTilePixel(int x, int y) {
        if (x < 0 || y < 0) return null;
        return getTile(x / Tile.WIDTH, y / Tile.WIDTH);
    }

    public Tile getRandomUnoccupiedTile() {
        Random r = new Random();
        Tile t;
        do {
            t = getTile(r.nextInt(numberOfTilesX), r.nextInt(numberOfTilesY));
        } while (!t.isPassable());
        return t;
    }

    public int getNumberOfTilesX() {
        return numberOfTilesX;
    }

    public int getNumberOfTilesY() {
        return numberOfTilesY;
    }
}
