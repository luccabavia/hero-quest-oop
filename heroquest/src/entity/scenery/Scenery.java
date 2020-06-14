package entity.scenery;

import entity.Entity;

/**
 * Parent class for every object that can be strutctural in the game's map.
 */

public abstract class Scenery extends Entity {

    protected int x;
    protected int y;

    public Scenery(int x, int y, String sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

}
