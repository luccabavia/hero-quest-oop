package entity.chest;

import entity.Entity;
import exceptions.MonsterHiddenInChestException;
import io.Display;
import item.Item;

import java.util.ArrayList;

public abstract class Chest extends Entity {

    protected Chest(int x, int y) {
        this.x = x;
        this.y = y;
        this.sprite = "Ch";
        this.hidden = false;
    }

    public abstract boolean hasContents() throws MonsterHiddenInChestException;

}
