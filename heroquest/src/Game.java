import dice.Dice;
import entity.Entity;
import entity.character.Character;
import entity.character.hero.*;
import entity.character.monster.Monster;
import entity.chest.Chest;
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

    public void startGameLoop() throws UnknownItemException {
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

    private void heroRound() throws UnknownItemException {
        //usePotion();
        heroMovement();
        changeHeroEquipment();
        // heroAction();
        //drawBoard();

    }

    private void monsterRound() {
        //monsterAction()
        monsterMovement();
        drawBoard();
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

    private void heroMovement() throws UnknownItemException {
        String command;
        int steps = 0;
        do {

            Display.print("Press r to roll the Dice");
            command = Keyboard.getInput();

        } while (!command.equalsIgnoreCase("r"));

        steps = Dice.rollRedDice(this.hero.getMovementDice());
        while (steps > 0 && !this.exit) {
            try {
                Display.print("You have "+ (steps) + " moves left.");
                Display.print(
                        "Use w, a, s, d keys to move, " +
                                "collect items with i and exit with q."
                );
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
                    case "i":
                        steps++;
                        this.collectItem();
                        break;
                    case "q":
                        this.exit = true;
                        break;
                    default:
                        steps++;
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

    private void changeHeroEquipment() {
        Display.print("Your current status is: ");
        Display.print(this.hero.getStatus());
        String changeEquipment = Keyboard.getInput("Would you like to " +
                "change equipment? [y/n] ");

        if (changeEquipment.equalsIgnoreCase("y")) {
            boolean continueChangingEquipment = true;
            do {
                Display.print(this.hero.getItemsInBag());
                int equipToChange = Integer.parseInt(
                        Keyboard.getInput("Which" +
                                "equipment would you like to change? " +
                                "\n[0] Armor;\n[1] Weapon;\n[2] Spell;")
                );
                switch (equipToChange) {
                    case 0:
                        changeHeroArmor();
                        break;
                    case 1:
                        changeHeroWeapon();
                        break;
                    case 2:
                        changeHeroSpell();
                        break;
                    default:
                        break;
                }
                if (Keyboard.getInput("Would you like to continue " +
                        "changing your equipment? [y/n] ").equalsIgnoreCase(
                                "n")) {
                    continueChangingEquipment = false;
                }
            } while(continueChangingEquipment);



        }
    }

    private void changeHeroArmor() {
        int index = Integer.parseInt(Keyboard.getInput("Which armor would " +
                "you like to equip? [index from bag]"));
        try {
            this.hero.setEquippedArmor(index);
        } catch (InvalidItemException e) {
            Display.printWarning(e.getMessage());
        }
    }

    private void changeHeroWeapon() {
        int index = Integer.parseInt(Keyboard.getInput("Which weapon would " +
                "you like to equip? [index from bag]"));

        OccupiedHand usedHand = null;
        int handIndex;
        boolean validSelection = false;
        do {
            handIndex = Integer.parseInt(Keyboard.getInput("In which hand " +
                    "would you like to equipped the weapon? \n[0] Left \n[1] " +
                    "Right \n[2] Both "));
            switch (handIndex) {
                case 0:
                    usedHand = OccupiedHand.LEFT;
                    validSelection = true;
                    break;
                case 1:
                    usedHand = OccupiedHand.RIGHT;
                    validSelection = true;
                    break;
                case 2:
                    usedHand = OccupiedHand.BOTH;
                    validSelection = true;
                    break;
                default:
                    break;
            }
        } while (!validSelection);

        try {
            this.hero.setEquippedWeapon(index, usedHand);
        } catch (InvalidItemException e) {
            Display.print(e.getMessage());
        }
    }

    private void changeHeroSpell() {
        int index = Integer.parseInt(Keyboard.getInput("Which spell would " +
                "you like to equip? [index from bag]"));
        try {
            this.hero.setEquippedSpell(index);
        } catch (InvalidItemException e) {
            Display.printWarning(e.getMessage());
        }
    }

    private void collectItem() throws UnknownItemException {

        Chest chest = this.hero.searchForItems();
        String input;
        if (chest != null) {
            while (chest.getSize() > 0) {
                chest.displayItems();
                input = Keyboard.getInput("Which items will you " +
                        "collect? Press q to stop collection... ");
                if (input.equalsIgnoreCase("q")) {break;}
                if (Integer.parseInt(input) < chest.getSize()) {
                    this.hero.addItemToBag(
                            chest.collectItems(Integer.parseInt(input))
                    );
                }
            }
            if (chest.getSize() == 0) {
                int[] pos = chest.getPosition();
                this.map.updateMap(pos[0], pos[1]);
            }
        }
        Display.print(this.hero.getStatus());
    }

    private void monsterMovement() {

        ArrayList<Monster> monsters = this.map.getMonster();
        for (Monster m : monsters) {
            m.move();
        }

    }
}
