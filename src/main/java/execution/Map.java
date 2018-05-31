package execution;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

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
                tiles.add(new Tile(x, y, false));
            }
        }
        startingTile = getTile(numberOfTilesX / 2, numberOfTilesY / 2);
    }

    public Tile getStartingTile() {
        return startingTile;
    }

    public void update(float delta) {
        tiles.forEach(tile -> tile.update(delta));
    }

    public void render(SpriteBatch sb) {
        tiles.forEach(tile -> tile.draw(sb));
    }

    public Tile getTile(int x, int y) {
        return tiles.get(y + numberOfTilesY * x);
    }

    public int getNumberOfTilesX() {
        return numberOfTilesX;
    }

    public int getNumberOfTilesY() {
        return numberOfTilesY;
    }
}
