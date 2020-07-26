package entity.character.hero;

import bag.Bag;
import dice.CombatDiceType;
import entity.character.*;
import entity.chest.Chest;
import exceptions.*;
import io.Display;
import item.Collectible;
import item.equipment.armor.Armor;
import item.equipment.potion.Potion;
import item.equipment.weapon.Weapon;
import map.Map;
import entity.character.Character;

/**
 * Parent class for any kind of Hero that can be created at the game.
 */
public abstract class Hero extends Character implements ArmorUser {

    private String name;
    private int movementDice;
    private Armor equippedArmor;
    private Weapon leftHandWeapon = null;
    private Weapon rightHandWeapon = null;
    private Weapon dualWieldingWeapon = null;
    private OccupiedHand usedHand;
    protected Bag bag;

    /**
     * Constructor method for hero parent class.
     * @param map map where game is played
     * @param x starting column
     * @param y starting row
     * @param sprite sprite used for visualization
     * @param bodyPoints int value of health
     * @param attackDice int amount of dices rolled on attack
     * @param defenseDice int amount of dices rolled on defense
     * @param movementDice int amount of dices rolled for movement
     * @param mindPoints int amount of intelligence points
     * @param name hero's name
     */
    public Hero(Map map, int x, int y, String sprite, int bodyPoints,
                int attackDice, int defenseDice, int movementDice,
                int mindPoints, String name) {
        super(map, x, y, sprite, bodyPoints, attackDice,
                defenseDice, mindPoints, CombatDiceType.WHITE_SHIELD);
        this.name = name;
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
        }
        return null;
    }

    /**
     * Add a collectible item to the hero's bag
     * @param item collectible item
     */
    public void addItemToBag(Collectible item) {
        this.bag.addItem(item);
    }

    /**
     * Get a string representation for items in bag
     * @return string containing items in bag
     */
    public String getItemsInBag() {
        return this.bag.displayItems();
    }

    /**
     * Check if hero has a potion in bag
     * @return if hero has potion
     */
    public boolean hasPotionInBag() {
    	return this.bag.hasPotion();
    }

    @Override
    public String getStatus() {

        String weapon="-", armor= "-";
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
        if (this.equippedArmor != null) {
            armor = this.equippedArmor.getCharacteristics();
        }
        String s = String.format("Name: %s, %s, Movement dice: %d, " +
                        "\nEquipment{ Weapon: %s, Armor: %s}, " +
                        "\nBag{ %s}",
                this.name, super.getStatus(), this.movementDice,
                weapon, armor,
                this.bag.getStatus());
        return s;
    }

    @Override
    public String getStatus(boolean summarized) {
        if (summarized) {
            String s = String.format("Name: %s, %s",
                    this.name, super.getStatus(true));
            return s;
        } else {
            return this.getStatus();
        }
    }

    /**
     * Get hero's movement dice count
     * @return int amount of movement dice hero has
     */
    public int getMovementDice() {
        return this.movementDice;
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
                weaponAttackBonus = this.leftHandWeapon.getAttack();
                if (this.leftHandWeapon.getDurability() <= 0) {
                    this.leftHandWeapon = null;
                    Display.printWarning("Your weapon is broken! " +
                            "Remember to" +
                            " equip another in the next round!");
                }
                break;
            case RIGHT:
                weaponAttackBonus = this.rightHandWeapon.getAttack();
                if (this.rightHandWeapon.getDurability() <= 0) {
                    this.rightHandWeapon = null;
                    Display.printWarning("Your weapon is broken! " +
                            "Remember to" +
                            " equip another in the next round!");
                }
                break;
            case BOTH:
                weaponAttackBonus = this.dualWieldingWeapon.getAttack();
                if (this.dualWieldingWeapon.getDurability() <= 0) {
                    this.dualWieldingWeapon = null;
                    Display.printWarning("Your weapon is broken! " +
                            "Remember to" +
                            " equip another in the next round!");
                }
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

    /**
     * Set weapon hand used for next attack
     * @param hand chosen hero's hand
     */
    public void setUsedHand(OccupiedHand hand) {
        this.usedHand = hand;
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
            this.bag.removeItem(armorIndexInBag);
        } catch (ClassCastException e) {
            throw new InvalidItemException("This item is not an armor!");
        }
    }

    /**
     * Set equipped armor
     * @param armor armor selected
     */
    protected void setArmor(Armor armor) {
        if (this.equippedArmor != null) {
            this.addItemToBag(this.equippedArmor);
            this.equippedArmor = null;
        }
        this.equippedArmor = armor;
    }

    /**
     * Implementation of interface method, set used weapon to selected hand
     * @param weaponIndexInBag int index from hero's bag
     * @param usedHand selected hand to equip weapon
     * @throws InvalidItemException thrown when an item that is not a weapon
     * is selected to be equipped
     */
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
            this.bag.removeItem(weaponIndexInBag);

        } catch (ClassCastException e) {
            throw new InvalidItemException(
                    "This item is not a weapon!");
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

    /**
     * Store weapon currently equipped in left hand in bag
     */
    private void storeLeftHandWeapon() {
        if (this.leftHandWeapon != null) {
            this.addItemToBag(this.leftHandWeapon);
            this.leftHandWeapon = null;
        }
    }

    /**
     * Store weapon currently equipped in right hand in bag
     */
    private void storeRightHandWeapon() {
        if (this.rightHandWeapon != null) {
            this.addItemToBag(this.rightHandWeapon);
            this.rightHandWeapon = null;
        }
    }

    /**
     * Store weapon currently equipped in both hands in bag
     */
    private void storeDualWieldingWeapon() {
        if (this.dualWieldingWeapon != null) {
            this.addItemToBag(this.dualWieldingWeapon);
            this.dualWieldingWeapon = null;
        }
    }

    @Override
    public int getArmorDefense() {
        int armorDefense = 0;
        if (this.equippedArmor != null) {
            armorDefense = this.equippedArmor.getDefense();
        }
        return armorDefense;
    }

    /**
     * Get character's defense value
     * @return character's defense value as an int
     */
    @Override
    public int getDefense() {
        return this.defenseDice + this.getArmorDefense();
    }

    @Override
    public int getAttack() {
        return this.getAttackDice(this.usedHand);
    }

    /**
     * Use potion
     * @param bagIndex int index in bag
     * @throws InvalidItemException thrown when a item that is not a potion
     * is selected
     */
    public void usePotion(int bagIndex) throws InvalidItemException {
        try {
            this.sufferEffect(((Potion) this.bag.getItem(bagIndex)).usePotion());
            this.bag.removeItem(bagIndex);
        } catch (ClassCastException e) {
            throw new InvalidItemException("This item is not a potion!");
        }

    }

}


