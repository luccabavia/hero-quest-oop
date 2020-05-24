package character.hero;

import io.Display;
import map.Map;

import java.util.Scanner;

import character.Character;
import io.Keyboard;
import dice.Dice;
import game.Game;

public class Hero extends Character {

    private String name;
    private int armor;
    private int[] hands = new int[2]; // 0 = Livre, 1 = Ocupada
    private Object[] bag;
    
    public Hero(int X, int Y, int movementDice, String sprite,
                String name) {
        super(X, Y, movementDice, sprite);
        this.name = name;
    }
    
    public static int[] returnPosition() {
    	int position[] = new int[4];
    	position[0] = X;
    	position[1] = Y;
    	position[2] = oldX;
    	position[3] = oldY;
    	return position;
    }

    /*
    Search for items in positions directly around the Hero, counter clockwise
     */
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
    	Display.print("Press r to roll the Dice");
    	Scanner keyboard = new Scanner(System.in);
    	String command = keyboard.nextLine ();
        while (!command.equalsIgnoreCase("r")) {
        	Display.print("Press r to roll the Dice");
        	command = keyboard.nextLine ();
        }
        
        int steps = Dice.rollRedDice(this.movementDice);
        Display.print("Do you have "+ steps + " movements");
        
        
        for (int i = 0; i < steps && this.takeStep(); i++) {
            Map.updateMap();
            Map.drawMap();
            Display.print("Do you have more "+ (steps-i-1) + " movements");
        }
    }

    /*
    Take a step in direction given by the user
     */
    private boolean takeStep() {
        int x =  X;
        int y =  Y;

        int newX, newY;
       	while (true) {
            String direction = Keyboard.getInput();
            if (direction.equalsIgnoreCase("q")) {
            	Game.exit();
            	Display.print("Finishing Game");
            	return false;
            } else if (direction.equalsIgnoreCase("w")) {
                newX = x - 1;
                newY = y;
                if (Map.isAvailable(newX, newY)) {
                     X = newX;
                     Y = newY;
                     oldX = x;
                     oldY = y;
                    break;
                } else {
                    Display.print("Position unavailable");
                }
            } else if (direction.equalsIgnoreCase("s")) {
                newX = x + 1;
                newY = y;
                if (Map.isAvailable(newX, newY)) {
                	X = newX;
                    Y = newY;
                    oldX = x;
                    oldY = y;
                    break;
                } else {
                    Display.print("Position unavailable");
                }
            } else if (direction.equalsIgnoreCase("a")) {
                newX = x;
                newY = y - 1;
                if (Map.isAvailable(newX, newY)) {
                	X = newX;
                    Y = newY;
                    oldX = x;
                    oldY = y;
                    break;
                } else {
                    Display.print("Position unavailable");
                }
            } else if (direction.equalsIgnoreCase("d")) {
                newX = x;
                newY = y + 1;
                if (Map.isAvailable(newX, newY)) {
                	X = newX;
                    Y = newY;
                    oldX = x;
                    oldY = y;
                    break;
                } else {
                    Display.print("Position unavailable");
                }
            } else {
                Display.print("Invalid input");
            }
        } 
       	return true;
    }

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
}


