package game;

import character.hero.Dummy;
import dice.Dice;
import io.Keyboard;
import map.Map;
import character.Character;
import character.hero.Hero;
import io.Display;

public class Game {

    private static boolean exit = false;
    private Map map;
    private Hero player1;
    private int steps;

    public Game() {


        
        configure();

    }
    
    public static void exit() {
    	exit = true;
    }

    public void start() {

        Display.print("Game started!");

        selectHero();
        map.placeHero(player1, 5, 5);

        while (!exit) {
            map.drawMap();
            move(player1);
        }
        Display.print("Game terminated. Bye!");
    }

    // Decides whether the map is standard or random
    public void configure(){

        int type;

        map = new Map();

    	Display.print("Select the Board Type:");
    	Display.print("[1] ~ Standard Map");
    	Display.print("[2] ~ Random Map");

    	type = Keyboard.getInt();

    	switch (type) {
            case 1:
                map.standardMap();
            case 2:
                map.randomMap();
        }
    }

    private void selectHero() {

        int h;
        Display.print("Select a Hero to play!");
        Display.print("[1] ~ Dummy");

        h = Keyboard.getInt();

        switch (h) {
            case 1:
                player1 = new Dummy(map);
        }
    }

    private void move(Hero h) {

        String command;
        do {

            Display.print("Press r to roll the Dice");
            command = Keyboard.getInput();

        } while (!command.equalsIgnoreCase("r"));

        steps = Dice.rollRedDice(h.getMovementDice());

        while (steps > 0) {

            Display.print("You have "+ (steps) + " moves left.");
            String action = Keyboard.getInput();

            if (action.equalsIgnoreCase("q")) {

                exit();
                Display.print("Finishing Game");

            } else if (action.equalsIgnoreCase("w")) {
                if (h.moveNorth()) steps--;
            } else if (action.equalsIgnoreCase("s")) {
                if (h.moveSouth()) steps--;
            } else if (action.equalsIgnoreCase("a")) {
                if (h.moveWest())  steps--;
            } else if (action.equalsIgnoreCase("d")) {
                if (h.moveEast())  steps--;
            } else {

                Display.print("press w to move in North direction!");
                Display.print("press s to move in South direction!");
                Display.print("press a to move in West direction!");
                Display.print("press d to move in East direction!");

            }

            map.drawMap();
        }
    }
}
