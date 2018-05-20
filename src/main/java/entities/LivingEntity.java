package entities;

import items.Item;

import java.util.Map;

abstract public class LivingEntity extends Entity {

    protected String name;
    protected short health;

    protected Map<String, Item> equipment;

    public LivingEntity(String spriteSheet, String initialSprite, String name, short health, short acceleration, short maxSpeed, float x, float y, int width, int height) {
        super(spriteSheet, initialSprite, acceleration, maxSpeed, x, y, width, height);
        this.name = name;
        this.health = health;
    }
}