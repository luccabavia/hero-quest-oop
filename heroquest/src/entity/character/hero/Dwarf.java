package entity.character.hero;

import item.equipment.weapon.ShortSword;
import map.Map;

/**
 * Define Dwarf hero type
 */
public class Dwarf extends Hero {

    public Dwarf(Map map, int x, int y, String name) {
        super(map, x, y, "Dw", 7, 2,
                2, 2, 3, name);
        this.setStartingEquipment();
    }

    @Override
    protected void setStartingEquipment() {
        this.setRightHandWeapon(new ShortSword());
    }

}
