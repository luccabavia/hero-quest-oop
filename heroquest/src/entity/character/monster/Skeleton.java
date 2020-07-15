package entity.character.monster;

import item.equipment.weapon.*;
import map.Map;

public class Skeleton extends Monster {

    public Skeleton(Map map, int x, int y) {
        super(map, x, y, "Sk", 4, 2,2,
                2);
        this.weapon.add(new ShortSword());
    }


    @Override
    public void attack() {}

    @Override
    public void defend() {

    }

    @Override
    protected void castSpell() {

    }

}
