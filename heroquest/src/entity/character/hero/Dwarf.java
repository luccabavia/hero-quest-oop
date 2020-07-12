package entity.character.hero;

import entity.item.equipment.weapon.ShortSword;
import map.Map;
import entity.item.equipment.weapon.*;

public class Dwarf extends Hero {

    public Dwarf(Map map, int x, int y, String name) {
        super(map, x, y, "Dw", 7, 2,
                2, 2, 3, name);
        this.weapon.add(new ShortSword());
    }

    @Override
    public void move(int steps) {

    }
}
