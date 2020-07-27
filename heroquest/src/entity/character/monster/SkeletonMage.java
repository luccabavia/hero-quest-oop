package entity.character.monster;

import entity.character.SpellCaster;
import exceptions.*;
import item.equipment.spell.MagicMissile;
import item.equipment.spell.Spell;
import item.equipment.spell.SpellEffectType;
import item.equipment.weapon.Fists;
import map.Map;
import target.Target;

import java.util.ArrayList;

/**
 * Define an Skeleton Mage can be set on Map
 *
 */
public class SkeletonMage extends Monster implements SpellCaster {

    private ArrayList<Spell> spells = new ArrayList<>();
    private Spell equippedSpell;

    public SkeletonMage(Map map, int x, int y) {
        super(map, x, y, "SM", 9999, 1, 3,
                3, 3);
        this.setStartingEquipment();
    }

    @Override
    protected void setStartingEquipment() {
        this.weapon = new Fists();
        for (int i = 0; i < 3; i++) {
            this.spells.add(new MagicMissile());
        }
        this.setEquippedSpell(0);
    }

    @Override
    public void hasSpells() throws NoSpellLeftException{
        if (this.equippedSpell == null && this.spells.size() == 0) {
        	throw new NoSpellLeftException("No spells remaining!");
        }
    }

    /**
     * Change equipped spell with other spell available in hero's bag
     * @param spellIndex spell's int index value in hero's bag
     */
    @Override
    public void setEquippedSpell(int spellIndex) {
        if (this.spells.size() > 0) {
            this.setSpell(this.spells.remove(spellIndex));
        }
    }

    @Override
    public void setSpell(Spell spell) {
        if (this.equippedSpell == null) {
            this.equippedSpell = spell;
        }
    }

    @Override
    public int getEquippedSpellRange() {
        return this.equippedSpell.getRange();
    }

    @Override
    public void castSpell(Target target) {
        if (this.equippedSpell != null) {
            this.equippedSpell.castSpell(target);
            if (!this.equippedSpell.hasCastsLeft()) {
                this.equippedSpell = null;
                this.setEquippedSpell(0);
            }
        }
    }

    @Override
    public SpellEffectType getEquippedSpellType() {
        return this.equippedSpell.getType();
    }

    @Override
    public int getEquippedSpellMaxTargets() {
        return this.equippedSpell.getMaxSpellTargets();
    }


}
