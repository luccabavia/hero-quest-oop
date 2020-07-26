package entity.chest;

import exceptions.MonsterHiddenInChestException;

public class TrapChest extends Chest {

    public TrapChest(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean hasContents() throws MonsterHiddenInChestException {
        String message = "The chest had a monster inside it!";
        throw new MonsterHiddenInChestException(
                message,
                new int[] {this.x, this.y}
        );
    }


}
