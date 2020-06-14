package entity.scenery;

/**
 * Class for every corridor inside any room that can be placed in the game's map.
 */

public class RoomTile extends Scenery {

    public RoomTile(int x, int y) {
        super(x, y, "\u001b[32m++\u001b[0m");
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }
}
