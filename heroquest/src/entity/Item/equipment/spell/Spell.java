package entity.Item.equipment.spell;

import entity.character.Character;

public abstract class Spell {

    private int casts;

    public Spell(int casts) {
        this.casts = casts;
    }

    public abstract void castSpell(Character target);

}
