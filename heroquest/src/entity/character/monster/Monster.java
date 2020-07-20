package entity.character.monster;

import exceptions.*;
import io.Display;
import item.equipment.spell.Spell;
import item.equipment.weapon.Weapon;
import map.Map;
import entity.character.Character;

import java.util.ArrayList;
import java.util.Random;

public abstract class Monster extends Character {

    protected int maxSteps;
    protected Weapon weapon;

    Monster(Map map, int x, int y, String sprite, int bodyPoints,
            int attackDice, int defenseDice, int maxSteps) {
        super(map, x, y, String.format("\u001b[35m%s\u001b[0m",sprite),
                bodyPoints, attackDice, defenseDice);
        this.maxSteps = maxSteps;
    }

    @Override
    public int getAttack() {
        return this.attackDice + this.weapon.getAttack();
    }

    public int getDefense() {
        return this.defenseDice;
    }

    @Override
    public int getEquippedWeaponRange() {
        return this.weapon.getRange();
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
                this.map.drawMap();
            } catch (PositionDoesNotExistException e){
//                Display.printWarning(e.getMessage());
                steps++;
            } catch (CannotWalkOverException e) {
//                Display.printWarning(e.getMessage());
                steps++;
            } catch (Exception e) {
//                Display.printWarning(e.getMessage());
                steps++;
            }
//            System.out.printf("Step %d ; (%d, %d)", steps, this.x, this.y);
//            System.out.println("");

        }
    }

}
