package entity.character.monster;

import map.Map;
import entity.character.Character;
import exception.*;
import io.Display;

import java.util.Random;

public abstract class Monster extends Character {

    Monster(Map map, int x, int y, String sprite, int bodyPoints,
            int attackDice, int defenseDice) {
        super(map, x, y, String.format("\u001b[35m%s\u001b[0m",sprite),
                bodyPoints, attackDice, defenseDice);
    }

    @Override
    public void move(int steps) {

        Random random;

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
        	} catch (PositionDoesNotExistException e){
        		steps++;
            } catch (CannotWalkOverException e) {
            	steps++;
            } 
        }
    }
}
