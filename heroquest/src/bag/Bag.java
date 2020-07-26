package bag;

import item.Collectible;
import item.equipment.potion.Potion;
import item.treasure.Treasure;

import java.util.ArrayList;

/**
 * Container for objects that a character can carry
 */
public class Bag {
	private int value = 0;

    private ArrayList<Collectible> items;

     /**
     * Constructor method for bag class.
     */
    public Bag() {
        this.items = new ArrayList<>();
    }

    /**
     * Add an item to the bag
     * @param item item to be added
     */
    public void addItem(Collectible item) {
    	try {
    		Treasure t = (Treasure) item;
    		this.value += t.getValue();
    	}catch (ClassCastException e ) {	
    	}
        this.items.add(item);
    }

    /**
     * Check if bag has a potion inside it
     * @return if bag has a potion inside it
     */
    public boolean hasPotion() {
    	for(int i = 0; i < this.items.size(); i++) {
    		try {
    			Potion p = (Potion) this.items.get(i);
    			return true;
    		}catch (ClassCastException e ) {	
        	}
    	}
    	return false;
    }

    /**
     * Get reference for item inside bag
     * @param itemIndex index inside of bag
     * @return collectible item
     */
    public Collectible getItem(int itemIndex) {
        return this.items.get(itemIndex);
    }

    /**
     * Remove item reference from bag
     * @param itemIndex index inside of bag
     */
    public void removeItem(int itemIndex) {
        this.items.remove(itemIndex);
    }

    /**
     * Get names of items in bag as string
     * @return string containing items in bag
     */
    public String displayItems() {
        StringBuilder s = new StringBuilder();
        s.append("Your bag's items: ");
        for (int i = 0; i < this.items.size(); i++) {
            s.append(String.format("[%d] %s", i, this.items.get(i).getName()));
            if (i != this.items.size()) { s.append("; "); }
        }
        return s.toString();
    }

    /**
     * Get bag value and total item count
     * @return string with total value and total item count
     */
    public String getStatus() {
        return String.format("Total value: %d, %d items in bag", this.value, this.items.size());
    }

    /**
     * Get amount of items inside of bag
     * @return amount of items inside of bag
     */
    public int getSize() {
        return this.items.size();
    }
}
