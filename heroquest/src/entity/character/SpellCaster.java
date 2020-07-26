package entity.character;

import exceptions.*;
import item.equipment.spell.Spell;
import item.equipment.spell.SpellEffectType;
import target.Target;

/**
 * Define interface for characters that can cast spells
 */
public interface SpellCaster {

    /**
     * Get the range of spell currently equipped for casting
     * @return int equipped spell range
     */
    int getEquippedSpellRange();

    /**
     * Execute currently equipped spell casting routine
     */
    void castSpell(Target target) throws CannotWalkOverException,
            PositionDoesNotExistException, IsTrapException;

    /**
     * Get type of spell equipped for use
     * @return spell effect type
     */
    SpellEffectType getEquippedSpellType();

    /**
     * Get spell from bag and set it for use with help of setSpell method
     * @param spellIndexInBag index for item in bag
     * @throws InvalidItemException exception thrown when a item that is not
     * a spell is selected
     */
    void setEquippedSpell(int spellIndexInBag) throws
            InvalidItemException;

    /**
     * Equip spell for use
     * @param spell spell selected for use
     */
    void setSpell(Spell spell);

    /**
     * Checks if character has spells left, equipped or not
     * @throws NoSpellLeftException thrown when character does not have
     * anymore spells
     */
    void hasSpells() throws NoSpellLeftException;

    /**
     * Get equipped spell's maxTargets attribute
     * @return max number of targets for equipped spell
     */
    int getEquippedSpellMaxTargets();
}
