package task04;

import task04.items.Item;

import java.util.Comparator;

public class ItemWeightComparator implements Comparator<Item> {
    @Override
    public int compare(Item a, Item b) {
        int result = Double.compare(a.getWeight(), b.getWeight());
        if(result == 0) {
            result = Integer.compare(b.getValue(), a.getValue());
        }
        return result;
    }
}
