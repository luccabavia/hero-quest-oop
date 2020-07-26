package entity.character;

import exceptions.*;
import item.equipment.spell.Spell;
import item.equipment.spell.SpellEffectType;
import target.Target;

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

    SpellEffectType getEquippedSpellType();

    void setEquippedSpell(int spellIndexInBag) throws
            InvalidItemException;

    void setSpell(Spell spell);

    void hasSpells() throws NoSpellLeftException;
    

    int getEquippedSpellMaxTargets();
}
