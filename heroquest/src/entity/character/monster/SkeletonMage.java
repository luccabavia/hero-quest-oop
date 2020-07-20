package entity.character.monster;

import entity.character.SpellCaster;
import item.equipment.spell.MagicMissile;
import item.equipment.spell.Spell;
import item.equipment.weapon.Fists;
import map.Map;

import java.util.ArrayList;

public class SkeletonMage extends Monster implements SpellCaster {

    private ArrayList<Spell> spells = new ArrayList<>();

    public SkeletonMage(Map map, int x, int y) {
        super(map, x, y, "SM", 2, 1, 3,
                3);
        this.setStartingEquipment();
    }

    @Override
    protected void setStartingEquipment() {
        this.weapon = new Fists();
        for (int i = 0; i < 3; i++) {
            this.spells.add(new MagicMissile());
        }

    }

    @Override
    public int getEquippedSpellRange() {
        return this.spells.get(0).getRange();
    }

    @Override
    public void castSpell() {

    }


}
