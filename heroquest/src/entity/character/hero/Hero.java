package entity.character.hero;

import bag.Bag;
import io.Display;
import map.Map;
import entity.character.Character;
import io.Keyboard;
import dice.Dice;

public abstract class Hero extends Character {

    private String name;
    private int armor;
    private int[] hands = new int[2]; // 0 = Livre, 1 = Ocupada
    private Bag bag;

    public Hero(Map map, int X, int Y, int movementDice, String sprite,
                String name) {
        super(map, X, Y, movementDice, sprite);
        this.name = name;
    }

    /*
    Search for items in positions directly around the Hero, counter clockwise
     */
    public void searchForItems() {

        int[][] adjacentPositions = new int[][] {
                {this.X, this.Y + 1},       // East
                {this.X - 1, this.Y + 1},   // Northeast
                {this.X - 1, this.Y},       // North
                {this.X - 1, this.Y - 1},   // Northwest
                {this.X, this.Y - 1},       // West
                {this.X + 1, this.Y - 1},   // Southwest
                {this.X + 1, this.Y},       // South
                {this.X + 1, this.Y + 1}    // Southeast
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
    @Override
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
        int x = this.X;
        int y = this.Y;

        int newX, newY;
        while (true) {
            String direction = Keyboard.getInput();
            if (direction.equalsIgnoreCase("w")) {
                newX = x - 1;
                newY = y;
                if (map.isAvailable(newX, newY)) {
                    this.X = newX;
                    this.Y = newY;
                    break;
                } else {
                    Display.print("Position unavailable");
                }
            } else if (direction.equalsIgnoreCase("s")) {
                newX = x + 1;
                newY = y;
                if (map.isAvailable(newX, newY)) {
                    this.X = newX;
                    this.Y = newY;
                    break;
                } else {
                    Display.print("Position unavailable");
                }
            } else if (direction.equalsIgnoreCase("a")) {
                newX = x;
                newY = y - 1;
                if (map.isAvailable(newX, newY)) {
                    this.X = newX;
                    this.Y = newY;
                    break;
                } else {
                    Display.print("Position unavailable");
                }
            } else if (direction.equalsIgnoreCase("d")) {
                newX = x;
                newY = y + 1;
                if (map.isAvailable(newX, newY)) {
                    this.X = newX;
                    this.Y = newY;
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


