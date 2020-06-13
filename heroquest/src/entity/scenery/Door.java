package entity.scenery;

public class Door extends Scenery {

    public Door(int x, int y) {
        super(x, y, "\u001b[36m[]\u001b[0m");
    }

    @Override
    public String getSprite() {
        return this.sprite;
    }

}
