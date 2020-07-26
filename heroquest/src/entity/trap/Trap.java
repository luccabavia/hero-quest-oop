package entity.trap;

import entity.Entity;

public class Trap extends Entity{

    private int damage;

    public Trap(int x, int y, int damage) {
        super(x, y, "||", true, true);
        this.damage = damage;
    }

    public int getDamage() {
        return this.damage;
    }
}
