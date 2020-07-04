package entity.item.equipment.spell;

import entity.character.Character;
import java.util.Random;

public class MagicMissile extends Spell {


    private final int MAX_TARGETS = 3;

    public MagicMissile() {
        super(1, -2);
    }

    @Override
    public void castSpell(Character[] targets) {
        int numberOfTargets = targets.length;
        switch (numberOfTargets) {
            case 1:
                super.castSpell(targets[0]);
                break;
            case 3:
                for (int i = 0; i < MAX_TARGETS; i++) {
                    super.castSpell(targets[i]);
                }
                break;
            default:
                Random rand = new Random();
                int firstTarget = rand.nextInt(2) + 1;
                int secondTarget = 3 - firstTarget;

                for (int i = 0; i < firstTarget; i++) {
                    super.castSpell(targets[0]);
                }

                for (int i = 0; i < secondTarget; i++) {
                    super.castSpell(targets[1]);
                }
        }
    }
}
