package item.equipment.spell;

import entity.character.Character;
import io.Keyboard;
import map.MovementDirection;

import java.util.Random;

public class Teleport extends Spell implements TransportSpell {

    public Teleport() {
        super(1, 0, "Teleport", 6);
    }

    @Override
    public void castSpell(int x, int y) {

    }
}
