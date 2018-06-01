package entities;

public abstract class HostileEntity extends LivingEntity {

    public HostileEntity(String spriteSheet, String initialSprite, String name, short health, short acceleration, short maxSpeed,
                         int damage, int movement, float x, float y, int width, int height) {
        super(spriteSheet, initialSprite, name, health, acceleration, maxSpeed, damage, movement, x, y, width, height);
    }
}
