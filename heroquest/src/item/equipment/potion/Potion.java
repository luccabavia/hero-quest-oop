package item.equipment.potion;

import item.Collectible;

public abstract class Potion implements Collectible {

    private int lifeEffect;
    private String name;

    public Potion(int lifeEffect, String name) {
        this.lifeEffect = lifeEffect;
        this.name = name;
    }

    public int usePotion() {
        return this.lifeEffect;
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
