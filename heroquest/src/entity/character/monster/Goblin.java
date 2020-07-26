package entity.character.monster;

import exceptions.IsTrapException;
import item.equipment.weapon.*;
import exceptions.CannotWalkOverException;
import exceptions.PositionDoesNotExistException;
import map.Map;
import map.MovementDirection;

import java.util.ArrayList;
import java.util.Random;

public class Goblin extends Monster {

    private ArrayList<Weapon> weaponPocket = new ArrayList<>();

    public Goblin(Map map, int x, int y) {
        super(map, x, y, "Go", 4, 3,1,
                2, 5);
        this.setStartingEquipment();
    }

    protected void setStartingEquipment() {
        int maxDaggersPackages = 3;
        for (int i = 0; i < maxDaggersPackages; i++) {
            weaponPocket.add(new Dagger());
        }
        this.weapon = this.weaponPocket.remove(0);
    }

    /**
     * Method to do Goblin kind of movements: This monster always 
     * by try to be closer of Hero
     */
    @Override
    public void move() {

        Random random = new Random();
        int[] positionHero = this.map.getHeroPosition();
        int steps = random.nextInt(this.maxSteps) + 1;

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
            } catch (CannotWalkOverException | IsTrapException e) {
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
                } catch (PositionDoesNotExistException | NullPointerException
                        | CannotWalkOverException | IsTrapException e1){
                    steps = 0;
                }
            }
        }
    }

    @Override
    public int getAttack() {
        int weaponAttack = this.weapon.getAttack();
        if (this.weapon.getDurability() <= 0) {
            if (this.weaponPocket.size() > 0) {
                this.weapon = this.weaponPocket.remove(0);
            } else {
                this.weapon = new Fists();
            }
        }
        return this.attackDice + weaponAttack;
    }

}
