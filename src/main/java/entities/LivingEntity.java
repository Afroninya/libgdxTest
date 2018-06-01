package entities;

import execution.Game;

abstract public class LivingEntity extends Entity {

    protected String name;
    protected short health;

    public LivingEntity(Game game, String spriteSheet, String initialSprite, String name, short health, short acceleration, short maxSpeed, float x, float y, int width, int height) {
        super(game, spriteSheet, initialSprite, acceleration, maxSpeed, x, y, width, height);
        this.name = name;
        this.health = health;
    }
}