package entity.character.monster;

import exceptions.*;
import io.Display;
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
//        System.out.printf("Initial (%d, %d)\n", this.x, this.y);
        Random random = new Random();
        int steps = random.nextInt(this.maxSteps) + 1;
//        System.out.printf("Steps %d", steps);
        while (steps > 0) {
            try {
                random = new Random();
                int number = random.nextInt(4) + 1;
                steps--;
                switch (number) {
                    case 1:
                        this.moveNorth();
                        break;
                    case 2:
                        this.moveSouth();
                        break;
                    case 3:
                        this.moveEast();
                        break;
                    default:
                        this.moveWest();
                        break;
                }
            } catch (PositionDoesNotExistException| CannotWalkOverException| IsTrapException e){
                steps++;
            }  
        }
    }

}
