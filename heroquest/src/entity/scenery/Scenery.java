package entity.scenery;

import entity.Entity;

/**
 * Parent class for every object that can be a structure in the game's map.
 */
public abstract class Scenery extends Entity {

    private final boolean walkOver;

    protected Scenery(int x, int y, String sprite, boolean walkOver,
                      boolean seeThrough) {
        super(x, y, sprite, false, seeThrough);
        this.walkOver = walkOver;
    }

    public boolean canWalkOver() {
        return this.walkOver;
    }

}
