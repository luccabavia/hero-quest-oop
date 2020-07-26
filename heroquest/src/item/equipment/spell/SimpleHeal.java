package item.equipment.spell;

import dice.Dice;
import target.Target;

public class SimpleHeal extends Spell {

    private final int NUMBER_OF_DICE = 1;

    public SimpleHeal() {
        super(1, 1,
                "SimpleHeal", 0, SpellEffectType.BUFFING, 1);
    }

    /**
     * Method to cast simple heal spell which is featured by give an healing to character
     */
    @Override
    public void castSpell(Target target) {
        int healValue =
                this.lifeEffect * Dice.rollRedDice(this.NUMBER_OF_DICE);
        target.getCharacter().sufferEffect(healValue);
        this.casts--;
    }
}
