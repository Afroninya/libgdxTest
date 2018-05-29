package execution;

import java.util.ArrayList;

public class Map {

    //the tilemap
    private ArrayList<Tile> tiles;
    //the width of the map in tiles
    private int numberOfTilesX;
    //the height of the map in tiles
    private int numberOfTilesY;

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
        //debug code to show it works
        for (Tile tile : tiles) {
            System.out.println(tile.toString());
        }
        System.out.println(getTile(5, 4).toString());
    }

    private Tile getTile(int x, int y) {
        return tiles.get(y + numberOfTilesY * x);
    }

}
