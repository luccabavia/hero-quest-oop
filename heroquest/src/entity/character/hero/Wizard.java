package entity.character.hero;

import entity.character.SpellCaster;
import exceptions.*;
import item.Collectible;
import map.Map;
import item.equipment.weapon.*;
import item.equipment.spell.*;
import target.Target;

/**
 * Define Wizard hero type
 */
public class Wizard extends Hero implements SpellCaster {

    private Spell equippedSpell;

    public Wizard(Map map, int x, int y, String name) {
        super(map, x, y, "Wz", 4,1, 2,
                2, 6, name);
        this.setStartingEquipment();

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
    protected void setStartingEquipment() {
        this.setRightHandWeapon(new Dagger());
        this.setSpell(new MagicMissile());
        Collectible[] items = new Collectible[] {new MagicMissile(),
                new MagicMissile(), new Fireball(this.map),
                new Teleport(this.map)};
        for (Collectible i : items) {
            this.addItemToBag(i);
        }
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
    public int getEquippedSpellMaxTargets() {
        return this.equippedSpell.getMaxSpellTargets();
    }

    @Override
    public int getEquippedSpellRange() {
        return this.equippedSpell.getRange();
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
    
}
