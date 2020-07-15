package item.equipment.potion;

import item.Item;

public abstract class Potion implements Item {

    private int lifeEffect;
    private String name;

    public Potion(int lifeEffect, String name) {
        this.lifeEffect = lifeEffect;
        this.name = name;
    }

    @Override
    public Item collect() {
        return this;
    }

    @Override
    public String getCharacteristics() {
        return String.format("%s -> LifeEffect: %d",
                this.name, this.lifeEffect);
    }

    @Override
    public String getName() {
        return name;
    }
}
