package entity.trap;

import entity.Entity;

public class Trap extends Entity{
    private int damage;

    public Trap(String sprite, int x, int y, int damage) {
        this.sprite = "Trap";
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.hidden = true;
    }

    public int getDamage() {
        return this.damage;
    }
}
