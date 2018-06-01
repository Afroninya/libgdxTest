package entities;

abstract public class LivingEntity extends Entity {

    protected String name;

    public LivingEntity(String spriteSheet, String initialSprite, String name, short health, short acceleration, short maxSpeed,
                        int damage, int movement, float x, float y, int width, int height) {
        super(spriteSheet, initialSprite, acceleration, maxSpeed, health, damage, movement, x, y, width, height);
        this.name = name;
    }
}