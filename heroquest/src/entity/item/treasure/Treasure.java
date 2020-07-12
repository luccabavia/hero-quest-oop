package entity.item.treasure;

import entity.item.Item;

public abstract class Treasure implements Item {

    private int value;

    public Treasure(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public Item collect() {
        return this;
    }
}
