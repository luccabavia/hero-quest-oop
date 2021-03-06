package item.treasure;

import item.Collectible;

/**
 * Parent class of all kind of Treasure
 */
public abstract class Treasure implements Collectible {

	/**
	 * Value of punctuation from each kind of Treasure
	 */
    private int value;
    private String name;

    public Treasure(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public String getCharacteristics() {
        return String.format("%s -> Value: %d", this.name, this.value);
    }

    @Override
    public String getName() {
        return name;
    }
}
