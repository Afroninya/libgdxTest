package entities;

public class NPC extends LivingEntity {

    private final short DEFAULT_MAX_SPEED = 10;

    public NPC() {
        this("Unknown");
    }
    
    public NPC(String name) {
        this(name, 0, 0);
    }

    public NPC(String name, float x, float y) {
        this(null, null, name, (short) 100, (short) 0, (short) 0, 0, 0, 16, 24);
    }

    public NPC(String spriteSheet, String initialSprite, String name, short health, short acceleration, short maxSpeed, float x, float y, int width, int height) {
        super(spriteSheet, initialSprite, name, health, acceleration, maxSpeed, x, y, width, height);
        this.maxSpeed = DEFAULT_MAX_SPEED;
    }

    public void handleMovement() {

    }
}
