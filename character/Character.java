package character;

import map.Map;
import dice.Dice;

import javax.swing.table.TableRowSorter;

public abstract class Character {

    protected Map map;
    protected int x;
    protected int y;
    protected int attackDice;
    protected int defenseDice;
    protected int movementDice;
    protected int bodyPoints;
    protected int mindPoints;
    protected String sprite;
    protected int steps;
//    private Weapon weapon;
//    private Spell[] spells;

    public Character(Map map, int movementDice, String sprite) {
        this.map = map;
        this.movementDice = movementDice;
        this.sprite = sprite;
    }

    public abstract int getMovementDice();

    public abstract void attack();

    public abstract void defend();

    protected abstract void takeDamage();

    protected abstract void castSpell();

    protected String getSprite() {
        return this.sprite;
    }

    public String getStatus() {
        String s = String.format("Positon: (%d, %d), Attack dice: %d, " +
                "Defense dice: %d, Body points: %d, Mind points: %d",
                this.x, this.y, this.attackDice, this.defenseDice,
                this.bodyPoints, this.mindPoints);
        return s;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
