package entity.item.equipment.spell;

import entity.character.Character;
import entity.item.Item;

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

    public Spell(int casts, int lifeEffect) {
        this.casts = casts;
    }

    public void castSpell(Character target) {
        target.sufferEffect(this.lifeEffect);
    }

    public void castSpell(Character[] targets) {

    }

    public Item collect() {
        return this;
    }

}
