package item.treasure;

import item.Item;

public abstract class Treasure implements Item {

    private int value;
    private String name;

    public Treasure(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return this.value;
    }

    public Item collect() {
        return this;
    }

    public String getCharacteristics() {
        return String.format("%s -> Value: %d", this.name, this.value);
    }

    @Override
    public String getName() {
        return name;
    }
}
