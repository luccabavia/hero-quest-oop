package entity.chest;

import entity.Entity;
import io.Display;
import item.Item;

import java.util.ArrayList;

public class Chest extends Entity {

    private ArrayList<Item> items = new ArrayList<>();

    public Chest(int x, int y) {
        this.x = x;
        this.y = y;
        this.sprite = "Ch";
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void displayItems() {
        StringBuilder s = new StringBuilder();
        int index = 0;
        for (Item i : items) {
            s.append(String.format("[%d] %s", index, i.getName()));
            if (index != items.size()) {s.append("; ");}
            index++;
        }
        Display.print(s.toString());
    }

    public int getSize() {
        return items.size();
    }

    public Item collectItems(int itemIndex) {
        Item i = items.get(itemIndex);
        items.remove(itemIndex);
        return i;
    }

}
