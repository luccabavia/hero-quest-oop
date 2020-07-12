import entity.Entity;
import entity.character.Character;
import entity.character.hero.*;
import entity.character.monster.*;
import exception.CannotWalkOverException;
import exception.PositionDoesNotExistException;
import map.Map;
import map.MapMode;
import io.*;
import dice.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private boolean exit;
    private Hero hero;
    private Map map;

    public Game() {
        try {
        	configure();
        } catch (PositionDoesNotExistException e) {
    		Display.print(e.getMessage());
    	} catch (CannotWalkOverException e) {
    		Display.print(e.getMessage());
    	}	

    }

    public void start() {
        exit = false;
        System.out.println("Game started!");

        int i = 0;
        while (!exit) {
            drawBoard();
            //readInput();
            movementPhase();
            i++;
            //Scanner keyboard = new Scanner(System.in);
            //System.out.print("Enter the command: ");
			//String command = keyboard.nextLine();
            if (i > 20) {
                exit = true;
            }
        }
        System.out.println("Game terminated. Bye!");
    }

    // Decides whether the map is standard or random
    private void configure() throws PositionDoesNotExistException, CannotWalkOverException {
        this.map = Map.getInstance();//MapMode.DEFAULT);
        this.map.setGameMode(MapMode.DEFAULT);
        this.hero = selectHero(HeroType.BARBARIAN);
        this.map.updateVisibility(hero.getPosition());
        //this.map.viewAllMap();
        try{
        	this.map.setEntity(this.hero);
        } catch (PositionDoesNotExistException e) {
    		throw e;
    	} catch (CannotWalkOverException e) {
    		throw e;
    	}	
    }

    private Hero selectHero(HeroType heroType) {
    	switch(heroType) {
    		case BARBARIAN:
    			return new Barbarian(this.map, 0, 0, "Claudio") ;
    		default:
    			return null;	
    	}
    }

    private void drawBoard(){
        //Display.print(hero.getStatus());
        this.map.drawMap();

    }

    private void readInput(){
    }

    private void movementPhase() {

    	moveHero();
        Random rand = new Random();

        ArrayList<Character> chars = new ArrayList<>();
        chars = this.map.getCharacter();

        for (Character c : chars) {
            c.move(1);//rand.nextInt(6) + 1);

        }

    }
    private void moveHero() {
            String command;
            int steps = 0;
            do {

                Display.print("Press r to roll the Dice");
                command = Keyboard.getInput();
                
            } while (!command.equalsIgnoreCase("r"));
            
            steps = Dice.rollRedDice(hero.getMovementDice());

            while (steps > 0) {
            	try {
	               Display.print("You have "+ (steps) + " moves left.");
	               Display.print("Next movement direction (using w, a, s, d keys): ");
	               String action = Keyboard.getInput();
	               steps--;
	               switch (action) {
	                case "w":
	                    this.hero.moveNorth();
	                    break;
	                case "s":
	                    this.hero.moveSouth();
	                    break;
	                case "d":
	                    this.hero.moveEast();
	                    break;
	                case "a":
	                	this.hero.moveWest();
	                    break;
	               }
	               this.map.updateVisibility(hero.getPosition());
	               this.map.drawMap();
		    	} catch (PositionDoesNotExistException e){
		    		steps++;
		    		Display.print(e.getMessage());
		        } catch (CannotWalkOverException e) {
		        	steps++;
		        	Display.print(e.getMessage());
		        } 
            }
        }
}
