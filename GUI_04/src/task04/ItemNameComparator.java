package task04;

import task04.items.Item;

import java.util.Comparator;

public class ItemNameComparator implements Comparator<Item> {
    @Override
    public int compare(Item a, Item b) {
        String name1 = a.getName();
        String name2 = b.getName();
        return name1.compareToIgnoreCase(name2);
    }
}
