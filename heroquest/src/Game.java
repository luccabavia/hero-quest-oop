import dice.Dice;
import entity.Entity;
import entity.character.Character;
import entity.character.hero.*;
import entity.character.monster.Monster;
import io.Display;
import io.Keyboard;
import map.Map;
import map.MapMode;
import exceptions.*;

import java.util.ArrayList;
import java.util.Random;

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

    public void startGameLoop() {
        exit = false;
        System.out.println("Game started!");
        StringBuilder finalMessage = new StringBuilder("Final message: ");

        int i = 0;
        while (!exit) {
            drawBoard();
            if (this.hero.getHealth() > 0){
                heroRound();
            } else {
                finalMessage.append("You have died! You loose!!");
                break;
            }
            if (this.map.hasMonsters()) {
                monsterRound();
            } else {
                finalMessage.append("All monsters killed! You win!!");
                break;
            }
            i++;
            if (i > 20) {
                exit = true;
            }
        }
        Display.print(finalMessage.toString());
        Display.print("Game terminated. Bye!");
    }

    private void heroRound() {
        //usePotion()
        heroMovement();
        // changeEquipment()
        // heroAction()
        //drawBoard();

    }

    private void monsterRound() {
        //monsterAction()
        monsterMovement();
        drawBoard();
        Display.print("\nFinal do MONSTER ROUND!");
    }

    // Decides whether the map is standard or random
    private void configure() throws
            CannotWalkOverException, PositionDoesNotExistException {
        this.map = Map.getInstance();//MapMode.DEFAULT);
        int[] startPosition = this.map.setGameMode(MapMode.DEFAULT);
        do {
            this.hero = selectHero(startPosition);
        } while (this.hero == null);
        this.map.placeHero(this.hero);
        this.map.updateVisibility();
        this.map.viewAllMap();
    }

    private Hero selectHero(int[] startPosition) {

        Hero hero = null;
        HeroType heroType = this.chooseHero();
        String heroName = Keyboard.getInput("Escolha o nome do seu herói: ");
        switch (heroType) {
            case ELF:
                hero = new Elf(this.map, startPosition[0],
                        startPosition[1], heroName);
                break;
            case DWARF:
                hero = new Dwarf(this.map, startPosition[0],
                        startPosition[1], heroName);
                break;
            case WIZARD:
                hero = new Wizard(this.map, startPosition[0],
                        startPosition[1], heroName);
                break;
            case BARBARIAN:
                hero = new Barbarian(this.map, startPosition[0],
                        startPosition[1], heroName);
                break;
        }

        return hero;
    }

    private HeroType chooseHero() {
        Display.print("Available hero types: " +
                "\n0 - Elf\n1 - Dwarf\n2 - Wizard\n3 - Barbarian");
        String choice = Keyboard.getInput("Choose your hero type: ");
        switch (choice) {
            case "0":
                return HeroType.ELF;
            case "1":
                return HeroType.DWARF;
            case "2":
                return HeroType.WIZARD;
            case "3":
                return HeroType.BARBARIAN;
        }
        return null;
    }

    private void drawBoard(){
        //Display.print(hero.getStatus());
        this.map.drawMap();

    }

    private void heroMovement() {
        String command;
        int steps = 0;
        do {

            Display.print("Press r to roll the Dice");
            command = Keyboard.getInput();

        } while (!command.equalsIgnoreCase("r"));

        steps = Dice.rollRedDice(this.hero.getMovementDice());
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
                this.map.updateVisibility();
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

    private void monsterMovement() {

        ArrayList<Monster> monsters = this.map.getMonster();
        for (Monster m : monsters) {
            m.move();
        }

    }
}
