package entity.character.monster;

import item.equipment.weapon.*;
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
        int steps = random.nextInt(this.maxSteps) + 1;
        MovementDirection[] failedAttempts = new MovementDirection[2];
        boolean failNorth = false;
        boolean failSouth = false;
        boolean failEast = false;
        boolean failWest = false;
        MovementDirection currDir = null;
        ArrayList<MovementDirection> failedDirections = new ArrayList<>();

        while (steps > 0) {

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

            try {
                switch (directions.get(0)) {
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
            } catch (PositionDoesNotExistException e){
                steps++;
//                Display.print(e.getMessage());
            } catch (CannotWalkOverException e) {
                steps++;
//                Display.print(e.getMessage());

                try {
                    switch (directions.get(1)) {
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
                } catch (PositionDoesNotExistException e1){
                    steps = 0;
//                    Display.print(e.getMessage());
                } catch (CannotWalkOverException e1) {
                    steps = 0;
//                    Display.print(e.getMessage());
                } catch (NullPointerException e1) {
                    steps = 0;
//                    Display.print(e.getMessage());
                }
            }
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
