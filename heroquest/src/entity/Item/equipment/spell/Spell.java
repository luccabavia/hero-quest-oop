package entity.Item.equipment.spell;

import entity.character.Character;

/**
 * Parent class of spells can be created at the game.
 */

public abstract class Spell {

    private int casts;

    public Spell(int casts) {
        this.casts = casts;
    }

    public abstract void castSpell(Character target);

}
