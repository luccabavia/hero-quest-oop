package entity.character.monster;

import entity.item.equipment.weapon.*;
import map.Map;

public class Goblin extends Monster {

    public Goblin(Map map, int x, int y) {
        super(map, x, y, "Go", 4, 3,1);
        this.weapon.add(new Dagger());
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
