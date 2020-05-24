package game;

import map.Map;
import character.Character;
import character.hero.Hero;
import io.Display;

public class Game {

    private static boolean exit = false;

    public Game() {
        
        configure();

    }
    
    public static void exit() {
    	exit = true;
    }

    public void start() {
        System.out.println("Game started!");
    	String name = "name";
    	Hero heroi = new Hero(6, 8, 1, name, name);
    	
        while (!exit) {
            drawBoard();
            heroi.move();
            //readInput();
            //updateBoard();
        }
        System.out.println("Game terminated. Bye!");
    }

    // Decides whether the map is standard or random
    public void configure(){
    	Map.importMap();
    }

    private void drawBoard(){
    	Map.drawMap();
    }

    private void readInput(){}

    private void updateBoard(){}
}
