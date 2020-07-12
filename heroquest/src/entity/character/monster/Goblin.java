package entity.character.monster;

import entity.character.Character;
import entity.character.hero.Hero;
import entity.item.equipment.weapon.*;
import exceptions.CannotWalkOverException;
import exceptions.PositionDoesNotExistException;
import io.Display;
import map.Map;
import map.MovementDirection;

import java.util.ArrayList;
import java.util.Random;

public class Goblin extends Monster {

    public Goblin(Map map, int x, int y) {
        super(map, x, y, "Go", 4, 3,1,
                5);
        this.weapon.add(new Dagger());
    }

    @Override
    public void move() {

        Random random = new Random();
        int[] positionHero = this.map.getHeroPosition();
        int steps = random.nextInt(this.maxSteps);
        while (steps > 0) {
            try {
                ArrayList<MovementDirection> directions = new ArrayList<>();
                if (this.x > positionHero[0]) {
                    directions.add(MovementDirection.NORTH);
                } else {
                    directions.add(MovementDirection.SOUTH);
                }
                if (this.y > positionHero[1]) {
                    directions.add(MovementDirection.WEST);
                } else {
                    directions.add(MovementDirection.EAST);
                }
                steps--;
                switch (directions.get(
                        random.nextInt(directions.size())
                )
                ) {
                    case NORTH:
                        this.moveNorth();
                        break;
                    case SOUTH:
                        this.moveSouth();
                        break;
                    case WEST:
                        this.moveWest();
                        break;
                    case EAST:
                        this.moveEast();
                        break;
                }
            } catch (Exception e) {
                steps++;
                Display.printWarning(e.getMessage());
            }
//            } catch (PositionDoesNotExistException e){
//                steps++;
//                Display.print(e.getMessage());
//            } catch (CannotWalkOverException e) {
//                steps++;
//                Display.print(e.getMessage());
//            }


        }


    }

    @Override
    public void attack() {

    }

    @Override
    public void defend() {

    }

    @Override
    protected void castSpell() {

    }
}
