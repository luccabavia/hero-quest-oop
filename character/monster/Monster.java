package character.monster;

import map.Map;
import character.Character;

public abstract class Monster extends Character {

    Monster(Map map, int movementDice, String sprite) {
        super(map, movementDice, sprite);
    }

    // Lógica do que cada tipo de mostro faz no início da rodada
    abstract void onRoundStart();
}
