package entities;

public class NPC extends LivingEntity {

    private final float DEFAULT_SPEED = 10;

    public NPC() {
        this(null, null, "Unknown", 0, 0);
    }
    
    public NPC(String name) {
        this(null, null, name, 0, 0);
    }

    public NPC(String name, float x, float y) {
        this(null, null, name, 0, 0);
    }

    public NPC(String spriteSheet, Enum defaultSprite, String name, float x, float y) {
        super(spriteSheet, defaultSprite, name, x, y);
        this.speed = DEFAULT_SPEED;
    }
}
