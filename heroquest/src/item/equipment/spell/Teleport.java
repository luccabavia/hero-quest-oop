package item.equipment.spell;

import entity.character.Character;
import io.Keyboard;
import map.MovementDirection;

import java.util.Random;

public class Teleport extends Spell implements TransportSpell {

    private int[] destination;

    public Teleport() {
        super(1, 0, "Teleport", 6);
    }

    @Override
    public void castSpell(int x, int y) {


//        int[] dupla = alguma_coisa_que_pede_input();

    }
}
