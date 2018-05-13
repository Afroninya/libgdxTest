package entities;

abstract public class LivingEntity extends Entity {

    private String name;
    private short health;


    public LivingEntity() {
        super();
        this.name = "Unknown";
    }

    public LivingEntity(String name) {
        super();
        this.name = name;
    }

    public LivingEntity(String name, int x, int y) {
        super(x, y);
        this.name = name;
    }
}
