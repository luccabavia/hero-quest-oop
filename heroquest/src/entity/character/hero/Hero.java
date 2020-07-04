package entity.character.hero;

import bag.Bag;
import io.Display;
import map.Map;
import entity.character.Character;
import io.Keyboard;
import dice.Dice;

/**
 * Parent class for any kind of Hero that can be created at the game.
 */

public abstract class Hero extends Character {

    /**
     * Constructor method for hero parent class.
     *
     * @param String name
     * @param int armor
     * @param int[] hands
     * @param  Bag bag
     */
    
    private String name;
    private int mindPoints;
    private int movementDice;
    private int armor;
    private int[] hands = new int[2]; // 0 = Livre, 1 = Ocupada
    private Bag bag;

    public Hero(Map map, int x, int y, String sprite, int bodyPoints,
                int attackDice, int defenseDice, int movementDice,
                int mindPoints, String name) {
        super(map, x, y, sprite, bodyPoints, attackDice,
                defenseDice);
        this.name = name;
        this.mindPoints = mindPoints;
        this.movementDice = movementDice;
    }

    /**
     * Method which searchs for collectible items in positions directly around the Hero, counter clockwise
     */
    public void searchForItems() {

        int[][] adjacentPositions = new int[][] {
                {this.x, this.y + 1},       // East
                {this.x - 1, this.y + 1},   // Northeast
                {this.x - 1, this.y},       // North
                {this.x - 1, this.y - 1},   // Northwest
                {this.x, this.y - 1},       // West
                {this.x + 1, this.y - 1},   // Southwest
                {this.x + 1, this.y},       // South
                {this.x + 1, this.y + 1}    // Southeast
        };

        for (int[] i: adjacentPositions) {
            if (map.hasItem(i)) {
                this.collectItem(i[0], i[1]);
                break;
            }
        }

    }

    @Override
    public String getStatus() {
        String s = String.format("Name: %s, %s", this.name, super.getStatus());
        return s;
    }

    /*
    Collect item and add to the bag
     */
    public void collectItem(int x, int y) {

    }

    /*
    Roll movement dices and take steps based on the returned value and user
    input
     */
    
    public void move() {
        int steps = Dice.rollRedDice(this.movementDice);
        for (int i = 0; i < steps; i++) {
            this.takeStep();
            map.drawMap();
        }
    }

    /*
    Take a step in direction given by the user
     */
    private void takeStep() {
        int x = this.x;
        int y = this.y;

        int newX, newY;
        while (true) {
            String direction = Keyboard.getInput();
            if (direction.equalsIgnoreCase("w")) {
                newX = x - 1;
                newY = y;
                if (map.isAvailable(newX, newY)) {
                    this.x = newX;
                    this.y = newY;
                    break;
                } else {
                    Display.print("Position unavailable");
                }
            } else if (direction.equalsIgnoreCase("s")) {
                newX = x + 1;
                newY = y;
                if (map.isAvailable(newX, newY)) {
                    this.x = newX;
                    this.y = newY;
                    break;
                } else {
                    Display.print("Position unavailable");
                }
            } else if (direction.equalsIgnoreCase("a")) {
                newX = x;
                newY = y - 1;
                if (map.isAvailable(newX, newY)) {
                    this.x = newX;
                    this.y = newY;
                    break;
                } else {
                    Display.print("Position unavailable");
                }
            } else if (direction.equalsIgnoreCase("d")) {
                newX = x;
                newY = y + 1;
                if (map.isAvailable(newX, newY)) {
                    this.x = newX;
                    this.y = newY;
                    break;
                } else {
                    Display.print("Position unavailable");
                }
            } else {
                Display.print("Invalid input");
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


