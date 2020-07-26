package exceptions;

import entity.character.monster.MonsterType;

public class MonsterHiddenInChestException extends Exception {

    private int[] monsterPosition;

    public MonsterHiddenInChestException() {
        super();
    }

    public MonsterHiddenInChestException(String message) {
        super(message);
    }

    public MonsterHiddenInChestException(Throwable cause) {
        super(cause);
    }

    public MonsterHiddenInChestException(String message, Throwable cause) {
        super(message, cause);
    }

    public MonsterHiddenInChestException(String message,
                                         int[] monsterPosition) {
        super(message);
        this.monsterPosition = monsterPosition;
    }

    public int[] getMonsterPosition() {
        return this.monsterPosition;
    }

}
