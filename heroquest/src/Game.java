import dice.CombatDiceType;
import dice.Dice;
import entity.character.Character;
import entity.character.SpellCaster;
import entity.character.hero.*;
import entity.character.monster.*;
import entity.chest.*;
import io.Display;
import io.Keyboard;
import item.equipment.spell.SpellEffectType;
import map.*;
import exceptions.*;
import target.Target;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Game {

    private boolean exit;
    private Hero hero;
    private Map map;

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

        while (!exit) {
            drawBoard();
            try {
                heroRound();
                monsterRound();
            } catch (MonstersDiedException e) {
                finalMessage.append("All monsters killed! You win!!");
                exit=true;
            } catch	(HeroDiedException e){
                finalMessage.append("You have died! You loose!!");
                exit=true;
            } catch (FinishGameException e) {
                finalMessage.append(e.getMessage());
                exit=true;
            }
        }
        drawBoard();
        Display.print(finalMessage.toString());
        Display.print("Game terminated. Bye!");
    }

    private void heroRound() throws HeroDiedException, MonstersDiedException,
            FinishGameException {
    	if(this.hero.hasPotionInBag())
    		usePotion();
        heroMovement();
        changeHeroEquipment();
        heroAction();
        drawBoard();

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

    /**
     * Sets whether the map is standard or random
     * @throws CannotWalkOverException
     * @throws PositionDoesNotExistException
     * @throws IsTrapException
     */
    private void configure() throws
            CannotWalkOverException, PositionDoesNotExistException,
            IsTrapException {

        this.map = Map.getInstance();
        int[] startPosition = new int[] {16, 18};
        MapMode mapMode;
        do {
            this.hero = selectHero(startPosition);
        } while (this.hero == null);

        do {
            mapMode = selectMapMode();
        } while (mapMode == null);

        this.map.setGameMode(mapMode, this.hero);
        this.map.updateVisibility();
    }

    private MapMode selectMapMode() {

        try {
            String message = "Choose the game mode: " +
                    "\n[0] Default: monster and items start at the same " +
                    "position every time\n[1] Random: monster and items start at" +
                    " random positions";
            int choice = Keyboard.getIntInput(message);
            switch (choice) {
                case 0:
                    return MapMode.DEFAULT;
                case 1:
                    return MapMode.RANDOM;
                default:
                    return null;
            }
        } catch (InputMismatchException e) {
            Display.printWarning("Invalid input!");
            return null;
        }
    }

    private Hero selectHero(int[] startPosition) {

        String heroName = Keyboard.getInput("Choose your hero name: ");
        try {
            String message = "Choose your hero type: " +
                    "\n[0] Elf\n[1] Dwarf\n[2] Wizard\n[3] Barbarian";
            int choice = Keyboard.getIntInput(message);
            switch (choice) {
                case 0:
                    hero = new Elf(this.map, startPosition[0],
                            startPosition[1], heroName);
                    break;
                case 1:
                    hero = new Dwarf(this.map, startPosition[0],
                            startPosition[1], heroName);
                    break;
                case 2:
                    hero = new Wizard(this.map, startPosition[0],
                            startPosition[1], heroName);
                    break;
                case 3:
                    hero = new Barbarian(this.map, startPosition[0],
                            startPosition[1], heroName);
                    break;
                default:
                    hero = null;
                    break;
            }
        } catch (InputMismatchException e) {
            Display.printWarning("Invalid input!");
        }

        return hero;
    }

    private void drawBoard(){
    	this.map.updateVisibility();
    	StringBuilder s = new StringBuilder();
    	String[][] map = this.map.drawMap();
    	
    	for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length - 1; j++) {
            	s.append(map[i][j]);
            	s.append(" ");
            }
            s.append(map[i][map[i].length - 1]);
            s.append("\n");
        }
    	
    	Display.print(s.toString());
        Display.print(this.hero.getStatus());

    }

    private void heroMovement() throws HeroDiedException, FinishGameException {
        String command;
        int steps = 0;
        String action;
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
                                "p to stop moving and go to action phase, or" +
                                " exit with q."
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
                    case "p":
                        steps = 0;
                        break;
                    case "q":
                        throw new FinishGameException("You chose the easy " +
                                "way out, game ended!");
                    default:
                        steps++;
                        break;
                }
                this.drawBoard();
            } catch (PositionDoesNotExistException |
                    CannotWalkOverException e) {
                steps++;
                Display.print(e.getMessage());
            } catch (IsTrapException e) {
                Display.printWarning(e.getMessage());
                int damage = e.getDamage();
                int[] newPosition = e.getTrapPosition();
                this.hero.sufferEffect(-damage);
                this.map.disarmTrap(newPosition[0], newPosition[1]);
                Display.print(this.hero.getStatus());
                this.map.updateVisibility();
                this.map.drawMap();
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
                    String[] contents = container.displayContents();
                    StringBuilder s = new StringBuilder();
                    int index = 0;
                    for (String i : contents) {
                        s.append(String.format("[%d] %s", index, i));
                        if (index != contents.length) {s.append("; ");}
                        index++;
                    }
                    Display.print(s.toString());

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
            Display.printWarning(e.getMessage());
            this.map.disarmTrapChest(e.getMonsterPosition());
        }
    }

    private void changeHeroEquipment() {
        Display.print("Your current status is: ");
        Display.print(this.hero.getStatus());
        String changeEquipment = Keyboard.getInput("Would you like to " +
                "change equipment? [y/n] ");
        
        String messageBoth = "Which" +
                "equipment would you like to change? " +
                "\n[0] Armor;\n[1] Weapon;\n[2] Spell;";
        String messageWeaponUserOnly = "Which" +
                "equipment would you like to change? " +
                "\n[0] Armor;\n[1] Weapon;\n";
        String message;
        

        if (changeEquipment.equalsIgnoreCase("y")) {
            boolean continueChangingEquipment = true;
            SpellCaster caster = null;
            
            do {
            	try {
                    caster = (SpellCaster) this.hero;
                    message = messageBoth;
                } catch (ClassCastException e) {
                    message = messageWeaponUserOnly;
                }
                try {
                    Display.print(this.hero.getItemsInBag());
                    int equipToChange = Keyboard.getIntInput(message);
                    
                    switch (equipToChange) {
                        case 0:
                            changeHeroArmor();
                            break;
                        case 1:
                            changeHeroWeapon();
                            break;
                        case 2:
                        	if (caster != null) {
	                        		try {
	                        			caster.hasSpells();
	                        			changeHeroSpell();
	                        		} catch (NoSpellLeftException e){
	                        			 Display.printWarning(e.getMessage());
	                        		} 
                        	}	
                            break;
                        default:
                            break;
                    }
                } catch (InputMismatchException e) {
                    Display.printWarning("Invalid index!");
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
        } catch (IndexOutOfBoundsException | InputMismatchException e) {
            Display.printWarning("Invalid index!");
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
            Display.printWarning(e.getMessage());
        } catch (IndexOutOfBoundsException | InputMismatchException e) {
            Display.printWarning("Invalid index!");
        }
    }

    private void changeHeroSpell() {
        int index = Keyboard.getIntInput("Which spell would " +
                "you like to equip? [index from bag]");
        try {
            ((SpellCaster) this.hero).setEquippedSpell(index);
        } catch (ClassCastException e) {
            Display.printWarning("Your hero cannot use spells!");
        } catch (InvalidItemException e) {
            Display.printWarning(e.getMessage());
        } catch (IndexOutOfBoundsException | InputMismatchException e) {
            Display.printWarning("Invalid index!");
        }
    }

    private void heroAction() throws MonstersDiedException {

        String messageBoth = "Select action type: " +
                "[0] Collect item; [1] Physical attack; [2] Spell casting";
        String messageWeaponUserOnly = "Select action type: " +
                "[0] Collect item; [1] Physical attack;";
        String message;
        boolean stop = false;
        SpellCaster caster = null;
        do {
            try {
                caster = (SpellCaster) this.hero;
                message = messageBoth;
            } catch (ClassCastException e) {
                message = messageWeaponUserOnly;
            }
            try { 
            	int mode = Keyboard.getIntInput(message);
            	switch (mode) {
                case 0:
                    this.collectItem();
                    stop = true;
                    break;
                case 1:
                    this.heroAttack();
                    stop = true;
                    break;
                case 2:
                    if (caster != null) {
                        try {
                            this.heroSpell();
                        } catch (NoSpellLeftException
                                | NullPointerException e) {
                            Display.printWarning(e.getMessage());
                        }
                        stop = true;
                    }
                    break;
            }
            } catch (InputMismatchException e) {
            	Display.printWarning("Invalid index!");
            }
            
        } while(!stop);

        if (!this.map.hasMonsters()) {
            throw new MonstersDiedException();
        }

    }

    private void heroAttack() {
        try {
            Monster monster =
                    selectTarget(hero.getEquippedWeaponRange(), 1).get(0);
            this.physicalCombat(this.hero, monster);
            this.afterActionReport(this.hero, monster);
        } catch (NoAvailableMonstersToAttackException e) {
            Display.printWarning(e.getMessage());
        }

    }
    
    private void physicalCombat(Character attacker, Character defender) {

        int atk = Dice.rollCombatDice(
                attacker.getAttack(),
                CombatDiceType.SKULL
        );

        int def = Dice.rollCombatDice(
                defender.getDefense(),
                defender.getDefenseType()
        );

        defender.sufferEffect(Math.min(0, -(atk - def)));
    }

    private void afterActionReport(Character attacker, Character defender) {
        String report = String.format(
                "\nAttacker: {%s}\n" +
                        "Defender: {%s}",
                attacker.getStatus(true),
                defender.getStatus(true)
        );
        Display.print(report);
    }

    private void heroSpell() throws NoSpellLeftException,
            NullPointerException {

        SpellCaster caster = (SpellCaster) this.hero;

        try{ 
        	caster.hasSpells();
            SpellEffectType type;
            try {
                type = caster.getEquippedSpellType();
            } catch (NullPointerException e) {
                throw new NullPointerException("No spell equipped!");
            }
            int range = caster.getEquippedSpellRange();
            Target target = new Target();

            switch (type) {
                case ATTACK:
                    try {
                    	Character c = this.selectTarget(range, 1).get(0);
                    	target.setCharacter(c);
                    	this.afterActionReport(this.hero, c);
                    } catch (NoAvailableMonstersToAttackException e) {
                        Display.printWarning(e.getMessage());
                        return;
                    }
                    break;
                case MULTI_ATTACK:
                    try {
                        int numberOfTargets = Keyboard.getIntInput(
                                String.format("You can select up to %d " +
                                        "targets, how many targets you want " +
                                        "to attack? ",
                                        caster.getEquippedSpellMaxTargets())
                        );
                        for (Monster m: this.selectTarget(range,
                                numberOfTargets)) {
                            target.setCharacter(m);
                            this.afterActionReport(this.hero, m);
                        }
                    } catch (NoAvailableMonstersToAttackException e) {
                        Display.printWarning(e.getMessage());
                        return;
                    }
                    break;
                case TRANSPORT:
                    target.setCharacter(this.hero);
                    target.setPosition(this.selectTargetPosition());
                    break;
                case BUFFING:
                    target.setCharacter(this.hero);
                    break;
            }

            try {
                int spellDices = Dice.rollRedDice(1);
                if (spellDices < this.hero.getMindPoints()) {
                    caster.castSpell(target);
                }
            } catch (CannotWalkOverException
                    | PositionDoesNotExistException
                    | IsTrapException e) {
                Display.printWarning(e.getMessage());
            }
        } catch (NoSpellLeftException e) {
        	Display.printWarning(e.getMessage());
        }
    
    }

    private void usePotion() {

        boolean stop = false;
        do {
            String answer = Keyboard.getInput("Do you want to use a potion? " +
                    "[y/n] ");
            if (answer.equalsIgnoreCase("y")) {
                Display.print(this.hero.getItemsInBag());
                int index = Integer.parseInt(
                        Keyboard.getInput("Which armor would " +
                        "you like to equip? [index from bag]"));
                try {
                    this.hero.usePotion(index);
                } catch (InvalidItemException e) {
                    Display.printWarning(e.getMessage());
                }
                stop = false;
            } else if (answer.equalsIgnoreCase("n")) {
                stop = true;
            }
        } while(!stop);
    }

    private int[] selectTargetPosition() {

        int x, y;
        boolean stop = false;
        do {
            Display.print("Select a visible position: ");
            x = Keyboard.getIntInput("  Select x: ");
            y = Keyboard.getIntInput("  Select y: ");

            if (this.map.isVisible(x, y)) {
                stop = true;
            } else {
                stop = false;
            }


        } while (!stop);

        return new int[] {x, y};
    }

    private void monsterAttack() throws HeroDiedException {

        ArrayList<Monster> monsters = this.map.getAttackersMonsters();

        
        for (Monster m : monsters) {
            try {
            	SkeletonMage skm = (SkeletonMage) m;
            	skm.hasSpells();
            	Target target = new Target();
            	target.setCharacter(this.hero);
            	int spellDices = Dice.rollRedDice(1);
            	if (spellDices < skm.getMindPoints()) {
            		skm.castSpell(target);
            	}
            } catch (ClassCastException | NoSpellLeftException e) {
                Display.printWarning(this.hero.getStatus(true));
                this.physicalCombat(m, this.hero);
            }
        }
        if (this.hero.getHealth() <= 0) {throw new HeroDiedException();}

    }


    private ArrayList<Monster> selectTarget(int range, int maxTargets) throws
            NoAvailableMonstersToAttackException {

        ArrayList<Monster> monsters = this.map.getMonstersToAttack(range);
        if (monsters.size() == 0) {
            throw new NoAvailableMonstersToAttackException("No monsters in " +
                    "range to attack");
        }

        ArrayList<Monster> targets = new ArrayList<>();
        for (int j = 0; j < maxTargets; j++) {
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
            targets.add(monsters.remove(index));
        }

        return targets;
    }

    private void monsterMovement() {

        ArrayList<Monster> monsters = this.map.getMonster();
        for (Monster m : monsters) {
            m.move();
        }

    }
}
