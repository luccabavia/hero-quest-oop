package entity.chest;

import entity.Entity;
import exceptions.MonsterHiddenInChestException;

public abstract class Chest extends Entity {

    protected Chest(int x, int y) {
        super(x, y, "Ch", false, false);
    }

    public abstract boolean hasContents() throws MonsterHiddenInChestException;

}
