package character.monster;

import map.Map;
import character.Character;

public abstract class Moster extends Character {

    Moster(Map map, int X, int Y, int movementDice, String sprite) {
        super(map, X, Y, movementDice, sprite);
    }

    // Lógica do que cada tipo de mostro faz no início da rodada
    abstract void onRoundStart();
}
