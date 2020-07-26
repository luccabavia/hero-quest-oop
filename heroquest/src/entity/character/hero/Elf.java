package entity.character.hero;

import entity.character.SpellCaster;
import exceptions.*;
import item.equipment.spell.*;
import map.Map;
import item.equipment.weapon.*;
import target.Target;

public class Elf extends Hero implements SpellCaster {

    private Spell equippedSpell;

    public Elf(Map map, int x, int y, String name) {
        super(map, x, y, "Ef", 6,2, 2,
                2, 4, name);
        this.setStartingEquipment();
    }

    protected void setStartingEquipment() {
        this.setRightHandWeapon(new ShortSword());
        this.setSpell(new SimpleHeal());
    }

    @Override
    public String getStatus() {
        StringBuilder spellString = new StringBuilder();
        if (this.equippedSpell != null) {
            spellString.append(this.equippedSpell.getCharacteristics());
        } else {
            spellString.append("-");
        }
        String status = String.format("%s, \nSpell {%s}", super.getStatus(),
                spellString.toString());
        return status;
    }

    @Override
    public int getEquippedSpellRange() {
        return this.equippedSpell.getRange();
    }

    /**
     * Change equipped spell with other spell available in hero's bag
     * @param spellIndexInBag spell's int index value in hero's bag
     */
    @Override
    public void setEquippedSpell(int spellIndexInBag) throws
            InvalidItemException {
        try {
            Spell newSpell = (Spell) this.bag.getItem(spellIndexInBag);
            this.setSpell(newSpell);
            this.bag.removeItem(spellIndexInBag);
        } catch (ClassCastException e) {
            throw new InvalidItemException("This item is not a spell!");
        }
    }

    @Override
    public void setSpell(Spell spell) {
        if (this.equippedSpell != null) {
            this.addItemToBag(this.equippedSpell);
            this.equippedSpell = null;
        }
        this.equippedSpell = spell;
    }

    @Override
    public void hasSpells() throws NoSpellLeftException {
        if (this.equippedSpell == null) {
            for (int i = 0; i < this.bag.getSize(); i++) {
                try {
                    Spell spell = (Spell) this.bag.getItem(i);
                    return;
                } catch (ClassCastException e) {
                    continue;
                }
            }
            throw new NoSpellLeftException("No spells remaining!");
        }
    }

    @Override
    public SpellEffectType getEquippedSpellType() {
        return this.equippedSpell.getType();
    }

    @Override
    public void castSpell(Target target) throws
            CannotWalkOverException, PositionDoesNotExistException,
            IsTrapException {
        this.equippedSpell.castSpell(target);
        if (!this.equippedSpell.hasCastsLeft()) {
            this.equippedSpell = null;
        }

    }

    @Override
    public int getEquippedSpellMaxTargets() {
        return this.equippedSpell.getMaxSpellTargets();
    }

}

