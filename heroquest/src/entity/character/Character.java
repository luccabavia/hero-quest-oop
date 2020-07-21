package entity.character;

import dice.CombatDiceType;
import entity.Entity;
import item.equipment.spell.Spell;
import item.equipment.weapon.Weapon;
import exceptions.*;
import map.Map;

import java.util.ArrayList;

/**
 * Definition of a Character that can be created in the game.
 */
public abstract class Character extends Entity implements WeaponUser {

    protected Map map;
    protected int attackDice;
    protected int defenseDice;
    protected int bodyPoints;
    protected CombatDiceType defenseType;

    /**
     * Constructor method for character parent class.
     *
     * @param map Map
     * @param x int
     * @param y int
     * @param sprite String
     */
    public Character(Map map, int x, int y, String sprite, int bodyPoints,
                     int attackDice, int defenseDice,
                     CombatDiceType defenseType) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.bodyPoints = bodyPoints;
        this.attackDice = attackDice;
        this.defenseDice = defenseDice;
        this.defenseType = defenseType;
    }

    protected abstract void setStartingEquipment();

    public void moveNorth() throws
            PositionDoesNotExistException, CannotWalkOverException,
            IsTrapException {
        try {
            if(this.map.isAvailable(x - 1, y)) {
                int oldX = this.x;
                this.x--;

                //            System.out.printf("Old (%d, %d); New (%d, %d)\n", oldX, this.y,
                //                    this.x, this.y);
                this.map.updateMap(oldX, this.y);
            }
        } catch (PositionDoesNotExistException | CannotWalkOverException |
                IsTrapException e){
            throw e;
        }
    }

    public void moveSouth() throws
            PositionDoesNotExistException, CannotWalkOverException,
            IsTrapException {
        try {
            if(this.map.isAvailable(x + 1, y)) {
                int oldX = this.x;
                this.x++;

                //                System.out.printf("Old (%d, %d); New (%d, %d)\n", oldX, this.y,
                //                        this.x, this.y);
                this.map.updateMap(oldX, this.y);
            }
        } catch (PositionDoesNotExistException | CannotWalkOverException |
                IsTrapException e) {
            throw e;
        }
    }

    public void moveEast() throws
            PositionDoesNotExistException, CannotWalkOverException,
            IsTrapException {
        try {
            if(this.map.isAvailable(x, y + 1)) {
                int oldY = this.y;
                this.y++;

                //System.out.printf("Old (%d, %d); New (%d, %d)\n", this.x, oldY,
                //        this.x, this.y);
                this.map.updateMap(this.x, oldY);
            }
        } catch (PositionDoesNotExistException | CannotWalkOverException |
                IsTrapException e) {
            throw e;
        }

    }

    public void moveWest() throws
            PositionDoesNotExistException, CannotWalkOverException,
            IsTrapException {
        try {
            if(this.map.isAvailable(x, y - 1)){
                int oldY = this.y;
                this.y--;

                //            System.out.printf("Old (%d, %d); New (%d, %d)\n", oldX, this.y,
                //                    this.x, this.y);
                this.map.updateMap(this.x, oldY);
            }
        } catch (PositionDoesNotExistException | CannotWalkOverException |
                IsTrapException e){
            throw e;
        }
    }

    public abstract int getAttack();

    public abstract int getDefense();

    public CombatDiceType getDefenseType() {
        return this.defenseType;
    }

    /**
     * Inflict effect into character's body points.
     * Effect can be positive, as a heal, or negative, as damage taken.
     * @param value int summed/subtracted to character's health
     */
    public void sufferEffect(int value) {
        this.bodyPoints += value;
    }

    /**
     * Get amount of body points the character currently has
     * @return int character's body points
     */
    public int getHealth() {
        return this.bodyPoints;
    }

    /**
     * Method to get character status.
     *
     * @return a String with all character information
     */
    public String getStatus() {
        String s = String.format(
                "Sprite: %s, Positon: (%d, %d), Body points: %d, Attack " +
                        "dice: %d, Defense dice: %d",
                this.sprite, this.x, this.y, this.bodyPoints, this.attackDice,
                this.defenseDice);
        return s;
    }

}
