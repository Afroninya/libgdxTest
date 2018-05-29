package entities;

public class HostileEntity extends LivingEntity {

    public HostileEntity(String spriteSheet, String initialSprite, String name, short health, short acceleration, short maxSpeed, float x, float y, int width, int height) {
        super(spriteSheet, initialSprite, name, health, acceleration, maxSpeed, x, y, width, height);
    }

    public void handleMovement() {
    }
}
