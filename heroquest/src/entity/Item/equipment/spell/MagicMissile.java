package entity.Item.equipment.spell;

import entity.character.Character;

public class MagicMissile extends Spell {

    private final int DAMAGE = -2;

    public MagicMissile() {
        super(1);
    }

    @Override
    public void castSpell(Character target) {
        target.sufferEffect(DAMAGE);
    }

}
