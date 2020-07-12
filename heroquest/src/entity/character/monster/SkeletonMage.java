package entity.character.monster;

import entity.item.equipment.spell.MagicMissile;
import entity.item.equipment.weapon.Fists;
import map.Map;

public class SkeletonMage extends Monster {

    public SkeletonMage(Map map, int x, int y) {
        super(map, x, y, "SM", 2, 1, 3,
                3);
        this.weapon.add(new Fists());
        this.spells.add(new MagicMissile());
    }

    @Override
    public void attack() {

    }

    @Override
    public void defend() {

    }

    @Override
    protected void castSpell() {

    }
}
