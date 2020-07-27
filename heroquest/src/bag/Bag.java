package bag;

import item.Collectible;
import item.equipment.potion.Potion;
import item.treasure.Treasure;

import java.util.ArrayList;

public class Bag {
	private int value = 0;
    
    /**
    * Class for objects which a hero can store.
    */
    private ArrayList<Collectible> items;

     /**
     * Constructor method for bag class.
     */
    public Bag() {
        this.items = new ArrayList<>();
    }

    public void addItem(Collectible item) {
    	try {
    		Treasure t = (Treasure) item;
    		this.value += t.getValue();
    	}catch (ClassCastException e ) {	
    	}
        this.items.add(item);
    }
    
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
    
    
    public Collectible getItem(int itemIndex) {
        return this.items.get(itemIndex);
    }

    public void removeItem(int itemIndex) {
        this.items.remove(itemIndex);
    }

    public String displayItems() {
        StringBuilder s = new StringBuilder();
        s.append("Your bag's items: ");
        for (int i = 0; i < this.items.size(); i++) {
            s.append(String.format("[%d] %s", i, this.items.get(i).getName()));
            if (i != this.items.size()) { s.append("; "); }
        }
        return s.toString();
    }

    public String getStatus() {
        return String.format("Total value: %d, %d items in bag", this.value, this.items.size());
    }

    public int getSize() {
        return this.items.size();
    }
}
