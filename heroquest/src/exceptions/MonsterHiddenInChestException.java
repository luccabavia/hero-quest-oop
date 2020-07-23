package exceptions;

import entity.character.monster.MonsterType;

public class MonsterHiddenInChestException extends Exception {

    private MonsterType monsterType;
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
                                         MonsterType monsterType,
                                         int[] monsterPosition) {
        super(message);
        this.monsterType = monsterType;
        this.monsterPosition = monsterPosition;
    }

    public MonsterType getMonsterType() {
        return this.monsterType;
    }

    public int[] getMonsterPosition() {
        return this.monsterPosition;
    }

}
