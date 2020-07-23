package entity.chest;

import exceptions.MonsterHiddenInChestException;
import io.Display;
import item.Item;

import java.util.ArrayList;

public class NormalChest extends Chest {

    protected ArrayList<Item> items = new ArrayList<>();

    public NormalChest(int x, int y) {
        super(x, y);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void displayContents() {
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

    @Override
    public boolean hasContents() throws MonsterHiddenInChestException {
        return this.getSize() > 0;
    }

    public Item collectItems(int itemIndex) {
        Item item = items.get(itemIndex).collect();
        items.remove(item);
        return item;
    }

}
