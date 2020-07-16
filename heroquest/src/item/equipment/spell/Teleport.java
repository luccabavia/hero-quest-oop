package item.equipment.spell;

import entity.character.Character;
import map.MovementDirection;

import java.util.Random;

public class Teleport extends Spell {

    public Teleport() {
        super(1, 0, "Teleport");
    }

    @Override
    public void castSpell(Character target) {
        Random rand = new Random();
        int steps = rand.nextInt(6);

        MovementDirection direction = MovementDirection.values()[
                rand.nextInt(4)
                ];

    }
}
