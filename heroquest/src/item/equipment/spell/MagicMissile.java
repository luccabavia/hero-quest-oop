package item.equipment.spell;

import target.Target;

import java.util.Random;

public class MagicMissile extends AttackSpell {

    public MagicMissile() {
        super(1, 2, "MagicMissile",
                3, SpellEffectType.MULTI_ATTACK, 3);
    }

    @Override
    public void castSpell(Target target) {
        int numberOfTargets = target.getSize();
        if (numberOfTargets == 1) {
            this.damageTarget(
                    target.getCharacter(),
                    maxTargets * lifeEffect
            );
        } else if (numberOfTargets == this.maxTargets) {
            for (int i = 0; i < maxTargets; i++) {
                this.damageTarget(
                        target.getCharacter(),
                        1 * lifeEffect
                );
            }
        } else {
            Random rand = new Random();
            int firstTarget = rand.nextInt(2) + 1;
            int secondTarget = 3 - firstTarget;

            this.damageTarget(
                    target.getCharacter(),
                    firstTarget * lifeEffect
            );
            this.damageTarget(
                    target.getCharacter(),
                    secondTarget * lifeEffect
            );
        }
        this.casts--;
    }
}
