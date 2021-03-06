package entities.util;

public enum CommonSprites {

    UP("_up"), DOWN("_down"), LEFT("_left"), RIGHT("_right");
    String value;

    CommonSprites(String value) { this.value = value; }

    public String toString() {
        return this.value;
    }
}
