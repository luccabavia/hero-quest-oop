package entity.character.hero;

import entity.item.equipment.spell.*;
import map.Map;
import entity.item.equipment.weapon.*;

public class Elf extends Hero {
    public Elf(Map map, int x, int y, String name) {
        super(map, x, y, "Ef", 6,2, 2,
                2, 4, name);
        this.weapon.add(new ShortSword());
        this.spells.add(new SimpleHeal());
    }

    @Override
    public void move(int steps) {

    }
}

