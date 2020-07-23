import battle.Battle;
import dice.Dice;
import entity.Entity;
import entity.character.Character;
import entity.character.hero.*;
import entity.character.monster.Goblin;
import entity.character.monster.Monster;
import entity.character.monster.Skeleton;
import entity.character.monster.SkeletonMage;
import entity.chest.Chest;
import entity.chest.NormalChest;
import entity.trap.Trap;
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
    private Battle battle;

    public Game() {
        try {
            configure();
        } catch (PositionDoesNotExistException | IsTrapException |
                CannotWalkOverException e) {
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
            try {
                heroRound();
                monsterRound();
            } catch (MonstersDiedException e) {
                finalMessage.append("All monsters killed! You win!!");
                break;
            } catch	(HeroDiedException e){
                finalMessage.append("You have died! You loose!!");
                break;

            }
        }
        Display.print(finalMessage.toString());
        Display.print("Game terminated. Bye!");
    }

    private void heroRound() throws HeroDiedException, MonstersDiedException {
        //usePotion();
        heroMovement();
        changeHeroEquipment();
        heroAction();
        //drawBoard();

    }

    private void monsterRound() throws MonstersDiedException,
            HeroDiedException {

        if(this.map.hasMonsters()) {
            monsterAttack();
            monsterMovement();
        }
        else {
            throw new MonstersDiedException();
        }
    }

    // Decides whether the map is standard or random
    private void configure() throws
            CannotWalkOverException, PositionDoesNotExistException,
            IsTrapException {
        this.map = Map.getInstance();//MapMode.DEFAULT);
        int[] startPosition = this.map.setGameMode(MapMode.RANDOM);
        do {
            this.hero = selectHero(startPosition);
        } while (this.hero == null);
        this.map.placeHero(this.hero);
        this.map.updateVisibility();
        this.map.viewAllMap();
        this.battle = new Battle();
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
        this.map.drawMap();
        Display.print(this.hero.getStatus());

    }

    private void heroMovement() throws HeroDiedException {
        String command;
        int steps = 0;
        String action= new String();
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
                action = Keyboard.getInput();
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
            } catch (PositionDoesNotExistException |
                    CannotWalkOverException e) {
                steps++;
                Display.print(e.getMessage());
            } catch (IsTrapException e) {
                int damage = 0;
                int[] heroPosition = this.hero.getPosition();
                Display.print(this.hero.getStatus());
                try {
                    switch (action) {
                        case "w":
                            damage = this.map.trapEffect(heroPosition[0] - 1,
                                    heroPosition[1]);
                            this.hero.moveNorth();
                            break;
                        case "s":
                            damage = this.map.trapEffect(heroPosition[0] + 1,
                                    heroPosition[1]);
                            this.hero.moveSouth();
                            break;
                        case "d":
                            damage = this.map.trapEffect(heroPosition[0],
                                    heroPosition[1] + 1);
                            this.hero.moveEast();
                            break;
                        case "a":
                            damage = this.map.trapEffect(heroPosition[0],
                                    heroPosition[1] - 1);
                            this.hero.moveWest();
                            break;
                    }
                    System.out.printf("GOTCHA!! You Suffer %d " +
                            "Damage from a Trap\n", damage);
                    Display.print(this.hero.getStatus());
                    this.map.updateVisibility();
                    this.map.drawMap();
                } catch (PositionDoesNotExistException |
                        CannotWalkOverException | IsTrapException e1){
                    steps++;
                }
            }
            if (this.hero.getHealth() <= 0) {
                throw new HeroDiedException();
            }
        }
    }

    private void collectItem() {

        Chest chest = this.hero.searchForItems();
        String input;
        try {
            if (chest != null && chest.hasContents()) {
                NormalChest container = (NormalChest) chest;
                while (chest.hasContents()) {
                    container.displayContents();
                    input = Keyboard.getInput("Which items will you " +
                            "collect? Press q to stop collection... ");
                    if (input.equalsIgnoreCase("q")) {
                        break;
                    }
                    if (Integer.parseInt(input) < container.getSize()) {
                        this.hero.addItemToBag(
                                container.collectItems(Integer.parseInt(input))
                        );
                    }
                }
                if (container.getSize() == 0) {
                    int[] pos = chest.getPosition();
                    this.map.updateMap(pos[0], pos[1]);
                }
            }
            Display.print(this.hero.getStatus());
        } catch (MonsterHiddenInChestException e) {
            int[] position = e.getMonsterPosition();
            Monster monster;
            Display.printWarning(e.getMessage());
            switch (e.getMonsterType()) {
                case GOBLIN:
                    monster = new Goblin(this.map, position[0], position[1]);
                    break;
                case SKELETON_MAGE:
                    monster = new SkeletonMage(this.map, position[0],
                            position[1]);
                    break;
                default:
                    monster = new Skeleton(this.map, position[0],
                            position[1]);
                    break;
            }
            try {
                this.map.removeEntity(monster);
                this.map.setEntity(monster);
            } catch (PositionDoesNotExistException
                    | IsTrapException
                    | CannotWalkOverException monsterE) {
                monsterE.printStackTrace();
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

    private void heroAction() throws MonstersDiedException {

        String mode = Keyboard.getInput("Select action type: p - physical " +
                "attack, s - spell casting, c - search for treasure");
        if (mode.equalsIgnoreCase("p")) {
            this.heroAttack();
        } else if (mode.equalsIgnoreCase("s")) {
            // FUNÇÃO PARA USAR SPELL
        }

        if (!this.map.hasMonsters()) {
            throw new MonstersDiedException();
        }


    }

    private void heroAttack() {
        try {
            Monster monster = selectTarget();
            battle.physicalCombat(this.hero, monster);
        } catch (NoAvailableMonstersToAttackException e) {
            Display.printWarning(e.getMessage());
        }

    }

    private void monsterAttack() throws HeroDiedException {

        ArrayList<Monster> monsters = this.map.getAttackersMonsters();
        int counter = 0;
        int[] monsterPosition;
//        Display.print("------------------Monsters Attackers ----------------");
        for (Monster m : monsters) {
            monsterPosition = m.getPosition();
//            System.out.printf("Monster (%d) Position: x:%d, y:%d\n", counter,
//                    monsterPosition[0], monsterPosition[1]);
            battle.physicalCombat(m, this.hero);
//            Keyboard.getInput("Digite para continuar");
            counter++;
        }
        if (this.hero.getHealth() <= 0) {throw new HeroDiedException();}

    }

    private int getHeroRangeToAttack() {
        return hero.getEquippedWeaponRange();
    }

    private Monster selectTarget() throws
            NoAvailableMonstersToAttackException {

        int range = getHeroRangeToAttack();
        ArrayList<Monster> monsters = this.map.getMonstersToAttack(range);
        if (monsters.size() == 0) {
            throw new NoAvailableMonstersToAttackException("No monsters in " +
                    "range to attack");
        }
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < monsters.size(); i++) {
            if (i != monsters.size() - 1) {
                s.append(String.format("[%d] %s ", i,
                        monsters.get(i).getStatus()));
            } else {
                s.append(String.format("[%d] %s, ", i,
                        monsters.get(i).getStatus()));
            }
        }

        Display.print(s.toString());
        boolean validSelection = false;
        int index = -1;
        do {
            try {
                index = Keyboard.getIntInput("Select monster to attack " +
                        "via list index: ");
                if (index >= 0) {
                    validSelection = true;
                }
            } catch (Exception e) {
                Display.printWarning("Not a valid list index, " +
                        "please input a positive integer");
            }
        } while (!validSelection);

        return monsters.get(index);
    }

    private void monsterMovement() {

        ArrayList<Monster> monsters = this.map.getMonster();
        for (Monster m : monsters) {
            m.move();
        }

    }
}
