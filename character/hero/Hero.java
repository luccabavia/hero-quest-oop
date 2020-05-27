package character.hero;

import io.Display;
import map.Map;

import character.Character;
import io.Keyboard;
import dice.Dice;
import game.Game;

public class Hero extends Character {

    private String name;
    private int armor;
    private int[] hands = new int[2]; // 0 = Livre, 1 = Ocupada
    private Object[] bag;
    
    public Hero(Map map, int movementDice, String sprite) {
        super(map, movementDice, sprite);
        map.startPosition(this);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getMovementDice() {
        return movementDice;
    }

    /*
    Search for items in positions directly around the Hero, counter clockwise
     */

    /*
    public void searchForItems() {

        int[][] adjacentPositions = new int[][] {
                {X, Y + 1},       // East
                {X - 1, Y + 1},   // Northeast
                { X - 1,  Y},       // North
                { X - 1,  Y - 1},   // Northwest
                { X,  Y - 1},       // West
                { X + 1,  Y - 1},   // Southwest
                { X + 1,  Y},       // South
                { X + 1,  Y + 1}    // Southeast
        };

        for (int[] i: adjacentPositions) {
            if (Map.hasItem(i)) {
                this.collectItem(i[0], i[1]);
                break;
            }
        }

    }
     */
    /*
    Collect item and add to the bag
     */
    public void collectItem(int x, int y) {

    }

    /*
    Roll movement dices and take steps based on the returned value and user
    input
     */
    @Override
    public void attack() {

    }

    @Override
    public void defend() {

    }

    @Override
    protected void takeDamage() {

    }

    @Override
    protected void castSpell() {

    }


    // Movement methods. Returns true if the hero change its position. False otherwise.
    public boolean moveNorth() {
        if (map.placeHero(this, x - 1, y)) {
            x--;
            return true;
        }
        return false;
    }

    public boolean moveWest() {
        if (map.placeHero(this, x, y - 1)) {
            y--;
            return true;
        }
        return false;
    }

    public boolean moveSouth() {
        if (map.placeHero(this, x + 1, y)) {
            x++;
            return true;
        }
        return false;
    }

    public boolean moveEast() {
        if (map.placeHero(this, x, y + 1)) {
            y++;
            return true;
        }
        return false;
    }
}


