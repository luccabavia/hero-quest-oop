package entity.scenery;

/**
 * Door object that can be placed in the game's map.
 */

public class Door extends Scenery {

    public Door(int x, int y) {
        super(x, y, "\u001b[36m[]\u001b[0m", true);
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

}
