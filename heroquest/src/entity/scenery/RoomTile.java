package entity.scenery;

public class RoomTile extends Scenery {

    public RoomTile(int x, int y) {
        super(x, y, "\u001b[32m++\u001b[0m");
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }
}
