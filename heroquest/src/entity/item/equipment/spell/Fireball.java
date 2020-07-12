package entity.item.equipment.spell;

import entity.character.Character;

public class Fireball extends Spell {

    private final int DAMAGE = -6;

    public Fireball() {
        super(1, -6);
    }

    @Override
    public void castSpell(Character target) {
        target.sufferEffect(DAMAGE);
    }
}
