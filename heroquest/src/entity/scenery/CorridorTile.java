package entity.scenery;

/**
 * Class for every corridor that can be placed in the game's map.
 */

public class CorridorTile extends Scenery {

    public CorridorTile(int x, int y) {
        super(x, y, "\u001b[34m==\u001b[0m");
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }
}
