package entity.Item.equipment.spell;

import dice.Dice;
import entity.character.Character;

public class SimpleHeal extends Spell {

    private final int NUMBER_OF_DICE = 1;
    private Dice dice;

    public SimpleHeal() {
        super(1);
    }

    @Override
    public void castSpell(Character target) {
        int healValue = dice.rollRedDice(this.NUMBER_OF_DICE);
        target.sufferEffect(healValue);
    }
}
