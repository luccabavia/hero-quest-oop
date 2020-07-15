package entity.character.hero;

import item.equipment.weapon.ShortSword;
import map.Map;

public class Dwarf extends Hero {

    public Dwarf(Map map, int x, int y, String name) {
        super(map, x, y, "Dw", 7, 2,
                2, 2, 3, name);
        this.weapon.add(new ShortSword());
    }

}
