package item.equipment.spell;

import dice.Dice;
import entity.character.Character;
/**
 * Parent class of all Spells used to attack 
 */
public abstract class AttackSpell extends Spell {


    public AttackSpell(int casts, int lifeEffect, String name,
                       int range, SpellEffectType type, int maxTargets) {
        super(casts, lifeEffect, name, range, type, maxTargets);
    }

    /**
     * Method to return damage of an Attack Spell
     * @param target
     * @param damage
     */
    protected void damageTarget(Character target, int damage) {
        int defense = Dice.rollCombatDice(target.getMindPoints(),
                target.getDefenseType());
        target.sufferEffect(Math.min(0, -(damage - defense)));
    }

}
