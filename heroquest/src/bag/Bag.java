package bag;

import item.Item;

import java.util.ArrayList;

public class Bag {
    
    /**
    * Class for objects which a hero can store.
    */
    private ArrayList<Item> items;

     /**
     * Constructor method for bag class.
     */
    public Bag() {

        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {

        this.items.add(item);
    }

    public Item getItem(int itemIndex) {
        return this.items.remove(itemIndex);
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
        return String.format("%d items in bag", this.items.size());
    }
}
