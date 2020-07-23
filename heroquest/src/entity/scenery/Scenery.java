package entity.scenery;

import entity.Entity;

/**
 * Parent class for every object that can be a structure in the game's map.
 */

public abstract class Scenery extends Entity {

    private boolean walkOver;

    public Scenery(int x, int y, String sprite, boolean walkOver) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.walkOver = walkOver;
        this.hidden = false;
    }

    public boolean canWalkOver() {
        return this.walkOver;
    }

}
