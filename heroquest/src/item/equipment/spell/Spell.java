package item.equipment.spell;

import item.Collectible;
import target.Target;

/**
 * Define spells than can be created in the game.
 */
public abstract class Spell implements Collectible {

    protected int casts;
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
    protected SpellEffectType type;
    protected int maxTargets;


    public Spell(int casts, int lifeEffect, String name, int range,
                 SpellEffectType type, int maxTargets) {
        this.casts = casts;
        this.lifeEffect = lifeEffect;
        this.name = name;
        this.range = range;
        this.type = type;
        this.maxTargets = maxTargets;
    }

    public SpellEffectType getType() {
        return this.type;
    }

    public int getRange() {
        return this.range;
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

    public abstract void castSpell(Target target);

    public boolean hasCastsLeft() {
        return this.casts > 0;
    }

    public int getMaxSpellTargets() {
        return this.maxTargets;
    }
}
