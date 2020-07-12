package entity.chest;

import entity.Entity;
import entity.item.Item;

import java.util.ArrayList;

public class Chest extends Entity {

    private ArrayList<Item> items;

    public Chest(int x, int y) {
        this.x = x;
        this.y = y;
        this.sprite = "Ch";
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public ArrayList<Item> collectItems() {
        // remover baú do mapa
        return items;
    }

}
