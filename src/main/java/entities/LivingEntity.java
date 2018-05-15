package entities;

abstract public class LivingEntity extends Entity {


    protected String name;
    protected short health;


    public LivingEntity(String spriteSheet, Enum defaultSprite, String name, float x, float y) {
        super(spriteSheet, defaultSprite, x, y);
        this.name = name;
    }
}
