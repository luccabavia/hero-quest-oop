package entity;


/**
 * Parent class for every object that can be placed in the game's map.
 */
public abstract class Entity {

    /**
     * Representation of entity used when displaying map.
     */
    protected String sprite;
    protected int x;
    protected int y;
    protected boolean hidden;

    /**
     * Get the representation of entity.
     *
     * @return sprite: String
     */
    public String getSprite() {
        return this.sprite;
    }

    public int[] getPosition() { return new int[] {x, y}; }

    public boolean isHidden() { return this.hidden; }

}
