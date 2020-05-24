package character;

import map.Map;
import dice.Dice;

public abstract class Character {

    protected Map map;
    protected static int X;
    protected static int Y;
    protected static int oldX;
    protected static int oldY;
    protected int attackDice;
    protected int defenseDice;
    protected int movementDice;
    protected int bodyPoints;
    protected int mindPoints;
    protected String sprite;
//    private Weapon weapon;
//    private Spell[] spells;

    public Character(int X, int Y, int movementDice, String sprite) {
        this.X = X;
        this.Y = Y;
        this.movementDice = movementDice;
        this.sprite = sprite;
    }

    public abstract void move();

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
                this.X, this.Y, this.attackDice, this.defenseDice,
                this.bodyPoints, this.mindPoints);
        return s;
    }

}
