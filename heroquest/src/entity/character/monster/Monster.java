package entity.character.monster;

import map.Map;
import entity.character.Character;

import java.util.Random;

public abstract class Monster extends Character {

    protected int maxSteps;

    Monster(Map map, int x, int y, String sprite, int bodyPoints,
            int attackDice, int defenseDice, int maxSteps) {
        super(map, x, y, String.format("\u001b[35m%s\u001b[0m",sprite),
                bodyPoints, attackDice, defenseDice);
        this.maxSteps = maxSteps;
    }

    public void move() {

        Random random = new Random();
        int steps = random.nextInt(this.maxSteps);
        while (steps > 0) {

            random = new Random();
            int number = random.nextInt(4) + 1;

            switch (number) {
                case 1:
                    if(this.moveNorth()) { steps--; }
                    break;
                case 2:
                    if(moveSouth()) { steps--; }
                    break;
                case 3:
                    if(moveEast()) { steps--; }
                    break;
                default:
                    if(moveWest()) { steps--; }
                    break;
            }
        }
    }

}
