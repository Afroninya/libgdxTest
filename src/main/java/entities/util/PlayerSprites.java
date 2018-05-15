package entities.util;

public enum PlayerSprites {
    SHIP_DOWN("ship_down"), SHIP_MIDDLE("ship_middle"), SHIP_UP("ship_up");

    String spriteID;

    PlayerSprites(String spriteID) { this.spriteID = spriteID; }

    public String toString() {
        return this.spriteID;
    }
}
