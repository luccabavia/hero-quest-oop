package item.equipment.potion;

import item.Collectible;

/**
 * Define potions than can be created in the game.
 */
public abstract class Potion implements Collectible {

	 /**
     * Effect caused on target's health.
     * If this int parameter is negative the effect is interpreted as damage,
     * if it is positive the effect is interpreted as a heal.
     */
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
