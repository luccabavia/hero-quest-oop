package entity.item.equipment.potion;

import entity.item.Item;

public abstract class Potion implements Item {

    private int lifeEffect;

    public Potion(int lifeEffect) {
        this.lifeEffect = lifeEffect;
    }

    @Override
    public Item collect() {
        return this;
    }
}
