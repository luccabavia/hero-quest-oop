package entity.character.hero;

import bag.Bag;
import entity.chest.Chest;
import exceptions.*;
import io.Display;
import item.Item;
import map.Map;
import entity.character.Character;
import io.Keyboard;
import dice.Dice;

/**
 * Parent class for any kind of Hero that can be created at the game.
 */

public abstract class Hero extends Character {

    /**
     * Constructor method for hero parent class.
     *
     * @param String name
     * @param int armor
     * @param int[] hands
     * @param  Bag bag
     */
    
    private String name;
    private int mindPoints;
    private int movementDice;
    private int armor;
    private int[] hands = new int[2]; // 0 = Livre, 1 = Ocupada
    private Bag bag;

    public Hero(Map map, int x, int y, String sprite, int bodyPoints,
                int attackDice, int defenseDice, int movementDice,
                int mindPoints, String name) {
        super(map, x, y, sprite, bodyPoints, attackDice,
                defenseDice);
        this.name = name;
        this.mindPoints = mindPoints;
        this.movementDice = movementDice;
        this.bag = new Bag();
    }

    /**
     * Method which searchs for collectible items in positions directly around the Hero, counter clockwise
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

    public void addItemToBag(Item item) throws UnknownItemException {
        this.bag.addItem(item);
    }

    @Override
    public String getStatus() {
        String s = String.format("Name: %s, %s, Movement dice: %d, " +
                        "Mind points: %s, Bag contents: %s" +
                        "", this.name, super.getStatus(), this.movementDice,
                this.mindPoints, this.bag.getStatus());
        return s;
    }

    public int getMovementDice() {
        return this.movementDice;
    }


    /*
    Collect item and add to the bag
     */
    public void collectItem() {

    }

    @Override
    public void attack() {

    }

    @Override
    public void defend() {

    }

    @Override
    protected void castSpell() {

    }
}


