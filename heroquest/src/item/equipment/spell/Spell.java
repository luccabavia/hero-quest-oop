package item.equipment.spell;

import entity.character.Character;
import item.Item;

/**
 * Define spells tha can be created in the game.
 */
public abstract class Spell implements Item {

    private int casts;
    private String name;
    /**
     * Effect caused on target's health.
     * If this int parameter is negative the effect is interpreted as damage,
     * if it is positive the effect is interpreted as a heal.
     */
    protected int lifeEffect;
    /**
     * How far the spell effect can be cast.
     */
    protected int range;

    public Spell(int casts, int lifeEffect, String name, int range) {
        this.casts = casts;
        this.lifeEffect = lifeEffect;
        this.name = name;
        this.range = range;
    }

    public int getRange() {
        return this.range;
    }

    public Item collect() {
        return this;
    }

    @Override
    public String getCharacteristics() {
        return String.format("%s -> LifeEffect: %d, Casts: %d",
                this.name, this.lifeEffect, this.casts);
    }

    @Override
    public String getName() {
        return name;
    }
}
