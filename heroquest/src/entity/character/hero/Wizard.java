package entity.character.hero;

import map.Map;
import item.equipment.weapon.*;
import item.equipment.spell.*;

public class Wizard extends Hero {

    public Wizard(Map map, int x, int y, String name) {
        super(map, x, y, "Wz", 4,1, 2,
                2, 6, name);
        this.weapon.add(new Dagger());
        /* Arrumar para ser tres punhais que
           quando lança é perdido */
        this.spells.add(new MagicMissile());
        this.spells.add(new MagicMissile());
        this.spells.add(new MagicMissile());
        this.spells.add(new Fireball());
        this.spells.add(new Teleport());

    }

}
