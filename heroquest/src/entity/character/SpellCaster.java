package entity.character;

public interface SpellCaster {

    /**
     * Get the range of spell currently equipped for casting
     * @return int equipped spell range
     */
    int getEquippedSpellRange();

    /**
     * Execute currently equipped spell casting routine
     */
    void castSpell();

}
