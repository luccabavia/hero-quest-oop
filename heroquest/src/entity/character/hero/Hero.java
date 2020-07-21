package entity.character.hero;

import bag.Bag;
import dice.CombatDiceType;
import entity.character.SpellCaster;
import entity.chest.Chest;
import exceptions.*;
import io.Display;
import io.Keyboard;
import item.Item;
import item.equipment.armor.Armor;
import item.equipment.spell.Spell;
import item.equipment.weapon.Weapon;
import map.Map;
import entity.character.Character;

/**
 * Parent class for any kind of Hero that can be created at the game.
 */
public abstract class Hero extends Character implements SpellCaster {


    
    private String name;
    private int mindPoints;
    private int movementDice;
    private Armor equippedArmor;
    private Weapon leftHandWeapon = null;
    private Weapon rightHandWeapon = null;
    private Weapon dualWieldingWeapon = null;
    private OccupiedHand usedHand;
    private Spell equippedSpell;
    private Bag bag;

    /**
     * Constructor method for hero parent class.
     * @param map
     * @param x
     * @param y
     * @param sprite
     * @param bodyPoints
     * @param attackDice
     * @param defenseDice
     * @param movementDice
     * @param mindPoints
     * @param name
     */
    public Hero(Map map, int x, int y, String sprite, int bodyPoints,
                int attackDice, int defenseDice, int movementDice,
                int mindPoints, String name) {
        super(map, x, y, sprite, bodyPoints, attackDice,
                defenseDice, CombatDiceType.WHITE_SHIELD);
        this.name = name;
        this.mindPoints = mindPoints;
        this.movementDice = movementDice;
        this.bag = new Bag();
    }

    /**
     * Method which searches for collectible items in positions directly
     * around the Hero, counter clockwise
     */
    public Chest searchForItems() {//throws NoChestFoundException {

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
            try {
                Chest chest = this.map.hasChest(i[0], i[1]);
                if (chest != null) { return chest; }
            } catch (PositionDoesNotExistException e) {
                continue;
            }
//            if (map.hasChest(i[0], i[1])) {
//                this.collectItem(i[0], i[1]);
//                break;
//            }
        }
        return null;
//        throw new NoChestFoundException("No chests locate around hero");
    }

    public void addItemToBag(Item item) {
        this.bag.addItem(item);
    }

    public String getItemsInBag() {
        return this.bag.displayItems();
    }

    @Override
    public String getStatus() {

        String weapon="-", spell="-", armor= "-";
        if (this.dualWieldingWeapon != null) {
            weapon = this.dualWieldingWeapon.getCharacteristics();
        } else {
            String leftWeapon="-", rightWeapon="-";
            if (this.leftHandWeapon != null) {
                 leftWeapon = this.leftHandWeapon.getCharacteristics();
            }
            if (this.rightHandWeapon != null) {
                rightWeapon = this.rightHandWeapon.getCharacteristics();
            }

            weapon = String.format("Left hand weapon: %s, Right hand weapon:" +
                    " %s", leftWeapon, rightWeapon);
        }
        if (this.equippedSpell != null) {
            spell = this.equippedSpell.getCharacteristics();
        }
        if (this.equippedArmor != null) {
            armor = this.equippedArmor.getCharacteristics();
        }
        String s = String.format("Name: %s, %s, Movement dice: %d, " +
                        "Mind points: %s, " +
                        "\nEquipment{ Weapon: %s, Spell: %s, " +
                        "Armor: %s}, " +
                        "\nBag contents: %s",
                this.name, super.getStatus(), this.movementDice,
                this.mindPoints, weapon, spell, armor,
                this.bag.getStatus());
        return s;
    }

    /**
     * Get hero's movement dice count
     * @return int amount of movement dice hero has
     */
    public int getMovementDice() {
        return this.movementDice;
    }

    /**
     * Display weapons held in hands
     * @return string with weapon info and which hand is holding which weapon
     */
    public String displayWeaponsInHands() {
        StringBuilder s = new StringBuilder();
        if (this.leftHandWeapon.equals(this.rightHandWeapon)) {
            s.append(String.format("Weapon held with both hands: %s",
                    this.leftHandWeapon.getDescription())
            );
        } else {
            if (this.leftHandWeapon != null) {
                s.append(String.format("Left hand: %s\n",
                        this.leftHandWeapon.getCharacteristics())
                );
            }
            if (this.rightHandWeapon != null) {
                s.append(String.format("Right hand: %s\n",
                        this.rightHandWeapon.getCharacteristics())
                );
            }
        }
        return s.toString();
    }

    /**
     * Get attack dice number based in weapon chosen
     * @param occupiedHand hand selected for next attack
     * @return base character attack value plus with weapon attack value
     */
    private int getAttackDice(OccupiedHand occupiedHand) {

        int weaponAttackBonus = 0;
        switch (occupiedHand) {
            case LEFT:
                weaponAttackBonus = this.leftHandWeapon.performAttack();
                break;
            case RIGHT:
                weaponAttackBonus = this.rightHandWeapon.performAttack();
                break;
            case BOTH:
                weaponAttackBonus = this.dualWieldingWeapon.performAttack();
                break;
        }
        return this.attackDice + weaponAttackBonus;
    }

    @Override
    public int getEquippedWeaponRange() {

        int weaponRange;
        if (this.usedHand == OccupiedHand.LEFT) {
            weaponRange = this.leftHandWeapon.getRange();
        } else if (this.usedHand == OccupiedHand.BOTH) {
            weaponRange = this.dualWieldingWeapon.getRange();
        } else {
            weaponRange = this.rightHandWeapon.getRange();
        }
        return weaponRange;
    }

    @Override
    public int getEquippedSpellRange() {
        return this.equippedSpell.getRange();
    }

    /**
     * Set weapon hand used for next attack
     * @param hand chosen hero's hand
     */
    public void setUsedHand(OccupiedHand hand) {
        this.usedHand = hand;
    }

    /**
     * Change equipped spell with other spell available in hero's bag
     * @param spellIndexInBag spell's int index value in hero's bag
     */
    public void setEquippedSpell(int spellIndexInBag) throws
            InvalidItemException {
        try {
            Spell newSpell = (Spell) this.bag.getItem(spellIndexInBag);
            this.setSpell(newSpell);
        } catch (ClassCastException e) {
            throw new InvalidItemException("This item cannot be equipped in " +
                    "this slot!");
        }
    }

    protected void setSpell(Spell spell) {
        if (this.equippedSpell != null) {
            this.addItemToBag(this.equippedSpell);
            this.equippedSpell = null;
        }
        this.equippedSpell = spell;
    }

    /**
     * Change equipped armor for an armor stored in bag
     * @param armorIndexInBag int
     */
    public void setEquippedArmor(int armorIndexInBag) throws
            InvalidItemException {
        try {
            Armor newArmor = (Armor) this.bag.getItem(armorIndexInBag);
            this.setArmor(newArmor);
        } catch (ClassCastException e) {
            throw new InvalidItemException("This item cannot be equipped in " +
                    "this slot!");
        }
    }

    protected void setArmor(Armor armor) {
        if (this.equippedArmor != null) {
            this.addItemToBag(this.equippedArmor);
            this.equippedArmor = null;
        }
        this.equippedArmor = armor;
    }

    public void setEquippedWeapon(int weaponIndexInBag, OccupiedHand usedHand)
            throws InvalidItemException {
        try {
            Weapon newWeapon = (Weapon) this.bag.getItem(weaponIndexInBag);
            if (newWeapon.getHands() == 2 && usedHand == OccupiedHand.LEFT ||
            newWeapon.getHands() == 2 && usedHand == OccupiedHand.RIGHT) {
                Display.printWarning("This weapon cannot be held with " +
                        "one hand. It will be used with both hands.");
                usedHand = OccupiedHand.BOTH;
            } else if (newWeapon.getHands() == 1 &&
                    usedHand == OccupiedHand.BOTH) {
                Display.printWarning("This weapon cannot be held with " +
                        "both hands. It will be used with the right hand.");
                usedHand = OccupiedHand.RIGHT;
            }

            switch (usedHand) {
                case LEFT:
                    setLeftHandWeapon(newWeapon);
                    break;
                case RIGHT:
                    setRightHandWeapon(newWeapon);
                    break;
                case BOTH:
                    setDualWieldingWeapon(newWeapon);
                    break;
            }

        } catch (ClassCastException e) {
            throw new InvalidItemException();
        }
    }

    /**
     * Change weapon equipped in left hand.
     * @param newWeapon weapon in bag that will be equipped
     */
    protected void setLeftHandWeapon(Weapon newWeapon) {
        this.storeDualWieldingWeapon();
        this.storeLeftHandWeapon();
        this.leftHandWeapon = newWeapon;
        this.usedHand = OccupiedHand.LEFT;
    }

    /**
     * Change weapon equipped in right hand.
     * @param newWeapon weapon in bag that will be equipped
     */
    protected void setRightHandWeapon(Weapon newWeapon) {

        this.storeDualWieldingWeapon();
        this.storeRightHandWeapon();
        this.rightHandWeapon = newWeapon;
        this.usedHand = OccupiedHand.RIGHT;


    }

    /**
     * Change weapon equipped, new weapon will be held with both hands
     * @param newWeapon weapon in bag that will be equipped
     */
    protected void setDualWieldingWeapon(Weapon newWeapon) {

            this.storeLeftHandWeapon();
            this.storeRightHandWeapon();
            this.storeDualWieldingWeapon();
            this.dualWieldingWeapon = newWeapon;
            this.usedHand = OccupiedHand.BOTH;

    }

    private void storeLeftHandWeapon() {
        if (this.leftHandWeapon != null) {
            this.addItemToBag(this.leftHandWeapon);
            this.leftHandWeapon = null;
        }
    }

    private void storeRightHandWeapon() {
        if (this.rightHandWeapon != null) {
            this.addItemToBag(this.rightHandWeapon);
            this.rightHandWeapon = null;
        }
    }

    private void storeDualWieldingWeapon() {
        if (this.dualWieldingWeapon != null) {
            this.addItemToBag(this.dualWieldingWeapon);
            this.dualWieldingWeapon = null;
        }
    }

    /**
     * Get character's defense value
     * @return character's defense value as an int
     */
    @Override
    public int getDefense() {
        int armorDefense = 0;
        if (this.equippedArmor != null) {
            armorDefense = this.equippedArmor.getDefense();
        }
        return this.defenseDice + armorDefense;
    }

    @Override
    public int getAttack() {

        return this.getAttackDice(this.usedHand);
    }

    @Override
    public void castSpell() {

    }
}


