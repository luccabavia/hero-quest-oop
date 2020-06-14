package entity.character;

import entity.Entity;
import map.Map;

/**
 * Parent class for every Character that can be created at the game.
 */

public abstract class Character extends Entity {

    protected Map map;
    protected int X;
    protected int Y;
    protected int attackDice;
    protected int defenseDice;
    protected int movementDice;
    protected int bodyPoints;
    protected int mindPoints;
//    private Weapon weapon;
//    private Spell[] spells;

    /**
     * Constructor method for character parent class.
     *
     * @param map Map
     * @param X int
     * @param Y int
     * @param movementDice int
     * @param sprite String
     */
    public Character(Map map, int X, int Y, int movementDice, String sprite) {
        this.map = map;
        this.X = X;
        this.Y = Y;
        this.movementDice = movementDice;
        this.sprite = sprite;
    }

    public abstract void move();

    public abstract void attack();

    public abstract void defend();

    public void sufferEffect(int value) {
        this.bodyPoints += value;
    }

    protected abstract void castSpell();

    /**
     * Method for get any character status.
     *
     * @return a String with all character information
     */
    
    public String getStatus() {
        String s = String.format(
                "Sprite: %s, Positon: (%d, %d), Attack dice: %d, " +
                "Defense dice: %d, Body points: %d, Mind points: %d",
                this.sprite, this.X, this.Y, this.attackDice, this.defenseDice,
                this.bodyPoints, this.mindPoints);
        return s;
    }

}
