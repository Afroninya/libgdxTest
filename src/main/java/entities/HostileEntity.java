package entities;

import execution.Game;

public class HostileEntity extends LivingEntity {

    public HostileEntity(Game game, String spriteSheet, String initialSprite, String name, short health, short acceleration, short maxSpeed, float x, float y, int width, int height) {
        super(game, spriteSheet, initialSprite, name, health, acceleration, maxSpeed, x, y, width, height);
    }

    public void handleMovement() {
    }
}
