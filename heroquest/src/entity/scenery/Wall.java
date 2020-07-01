package entity.scenery;

/**
 * Wall object, to separate the inside of a room from other spaces, that can be placed in the game's map.
 */

public class Wall extends Scenery {

    public Wall(int x, int y) {
        super(x, y, "\u001b[31m//\u001b[0m");
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }
}
