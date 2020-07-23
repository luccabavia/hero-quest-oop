package entity.chest;

import entity.character.monster.MonsterType;
import exceptions.MonsterHiddenInChestException;
import java.util.Random;

public class TrapChest extends Chest {

    private boolean randomized;

    public TrapChest(int x, int y, boolean randomized) {
        super(x, y);
        this.randomized = randomized;
    }

    @Override
    public boolean hasContents() throws MonsterHiddenInChestException {
        MonsterType monsterType;
        if (this.randomized) {
            Random random = new Random();
            switch (random.nextInt(3)) {
                case 1:
                    monsterType = MonsterType.GOBLIN;
                    break;
                case 2:
                    monsterType = MonsterType.SKELETON_MAGE;
                    break;
                default:
                    monsterType = MonsterType.SKELETON;
            }
        } else {
            monsterType = MonsterType.SKELETON;
        }
        String message = String.format(
                "The chest had a monster inside it! It " +
                "was a %s!", monsterType.name()
        );
        throw new MonsterHiddenInChestException(
                message,
                monsterType
        );
    }


}
