package item.equipment.spell;

import entity.character.Character;
import item.Item;

/**
 * Define spells tha can be created in the game.
 */

public abstract class Spell implements Item {

    private int casts;

    /**
     * Effect caused on target's health.
     * If this int parameter is negative the effect is interpreted as damage,
     * if it is positive the effect is interpreted as a heal.
     */
    protected int lifeEffect;
    private String name;

    public Spell(int casts, int lifeEffect, String name) {
        this.casts = casts;
        this.lifeEffect = lifeEffect;
        this.name = name;
    }

    public void castSpell(Character target) {
        target.sufferEffect(this.lifeEffect);
    }

    public void castSpell(Character[] targets) {

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
