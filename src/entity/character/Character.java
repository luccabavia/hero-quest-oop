package entity.character;

import entity.Entity;
import entity.item.equipment.spell.Spell;
import entity.item.equipment.weapon.Weapon;
import map.Map;
import exception.*;

import java.util.ArrayList;
/**
 * Definition of a Character that can be created in the game.
 */

public abstract class Character extends Entity {

    protected Map map;
    protected int attackDice;
    protected int defenseDice;
    protected int bodyPoints;
    protected ArrayList<Weapon> weapon = new ArrayList<>();
    protected ArrayList<Spell> spells = new ArrayList<>();

    /**
     * Constructor method for character parent class.
     *
     * @param map Map
     * @param x int
     * @param y int
     * @param sprite String
     */
    public Character(Map map, int x, int y, String sprite, int bodyPoints,
                     int attackDice, int defenseDice) {
        this.map = map;
        this.x = x;
        this.y = y;
        this.sprite = sprite;
        this.bodyPoints = bodyPoints;
    }

    public abstract void move(int steps);

    public void moveNorth() throws PositionDoesNotExistException, CannotWalkOverException {
        try {	
        	if(this.map.isAvailable(x - 1, y)) {
	    		int oldX = this.x;
	            this.x--;
	
	//            System.out.printf("Old (%d, %d); New (%d, %d)\n", oldX, this.y,
	//                    this.x, this.y);
	            this.map.updateMap(oldX, this.y);
        	}
        } catch (PositionDoesNotExistException e){
            throw e;
        } catch (CannotWalkOverException e) {
            throw e;
        }
    }
    public void moveSouth() throws PositionDoesNotExistException, CannotWalkOverException {
            try {	
            	if(this.map.isAvailable(x + 1, y)) {
	        		int oldX = this.x;
	                this.x++;
	
	//                System.out.printf("Old (%d, %d); New (%d, %d)\n", oldX, this.y,
	//                        this.x, this.y);
	                this.map.updateMap(oldX, this.y);
            	}
            } catch (PositionDoesNotExistException e){
                throw e;
            } catch (CannotWalkOverException e) {
                throw e;
            } 
    }

    public void moveEast() throws PositionDoesNotExistException, CannotWalkOverException {
        try {	
    		if(this.map.isAvailable(x, y + 1)) {
	    		int oldY = this.y;
	            this.y++;
	
	          //System.out.printf("Old (%d, %d); New (%d, %d)\n", this.x, oldY,
	          //        this.x, this.y);
	            this.map.updateMap(this.x, oldY);
    		}    
        } catch (PositionDoesNotExistException e){
            throw e;
        } catch (CannotWalkOverException e) {
            throw e;
        } 
    
    }
    public void moveWest() throws PositionDoesNotExistException, CannotWalkOverException {
        try {	
        	if(this.map.isAvailable(x, y - 1)){
	    		int oldY = this.y;
	            this.y--;
	
	//            System.out.printf("Old (%d, %d); New (%d, %d)\n", oldX, this.y,
	//                    this.x, this.y);
	            this.map.updateMap(this.x, oldY);
        	}
        } catch (PositionDoesNotExistException e){
            throw e;
        } catch (CannotWalkOverException e) {
            throw e;
        }
    }

    public abstract void attack();

    public abstract void defend();

    public void sufferEffect(int value) {
        this.bodyPoints += value;
    }

    protected abstract void castSpell();

    /**
     * Method to get character status.
     *
     * @return a String with all character information
     */
    
    public String getStatus() {
        String s = String.format(
                "Sprite: %s, Positon: (%d, %d), Attack dice: %d, " +
                "Defense dice: %d, Body points: %d",
                this.sprite, this.x, this.y, this.attackDice, this.defenseDice,
                this.bodyPoints);
        return s;
    }

}
