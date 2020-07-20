package entity.character.hero;

import item.equipment.spell.*;
import map.Map;
import item.equipment.weapon.*;

public class Elf extends Hero {
    public Elf(Map map, int x, int y, String name) {
        super(map, x, y, "Ef", 6,2, 2,
                2, 4, name);
        this.setStartingEquipment();
    }

    protected void setStartingEquipment() {
        this.setRightHandWeapon(new ShortSword());
        this.setSpell(new SimpleHeal());
    }

}

