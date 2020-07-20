package item.equipment.spell;

import entity.character.Character;
import java.util.Random;

public class MagicMissile extends Spell implements AttackSpell {


    private final int MAX_TARGETS = 3;

    public MagicMissile() {
        super(1, -2, "MagicMissile", 3);
    }

    @Override
    public void castSpell(Character target) {
        target.sufferEffect(MAX_TARGETS*lifeEffect);
    }

    @Override
    public void castSpell(Character[] targets) {
        int numberOfTargets = targets.length;
        switch (numberOfTargets) {
            case 1:
                this.castSpell(targets[0]);
                break;
            case 3:
                for (int i = 0; i < MAX_TARGETS; i++) {
                    targets[i].sufferEffect(lifeEffect);
                }
                break;
            default:
                Random rand = new Random();
                int firstTarget = rand.nextInt(2) + 1;
                int secondTarget = 3 - firstTarget;

                for (int i = 0; i < firstTarget; i++) {
                    targets[0].sufferEffect(lifeEffect);
                }

                for (int i = 0; i < secondTarget; i++) {
                    targets[1].sufferEffect(lifeEffect);
                }
        }
    }
}
