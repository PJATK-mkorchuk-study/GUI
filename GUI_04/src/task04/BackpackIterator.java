package task04;

import task04.items.Item;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BackpackIterator  implements Iterator<Item> {
    private final Item[] items;
    private final int itemCount;
    private int currentIndex;

    BackpackIterator(Item[] items, int itemCount) {
        this.items = items;
        this.itemCount = itemCount;
        currentIndex = 0;
        skipWorthless();
    }

    private void skipWorthless() {
        while (currentIndex < itemCount && items[currentIndex].isWorthless()) {
            currentIndex++;
        }
    }

    public boolean hasNext() {
        return currentIndex < itemCount;
    }

    public Item next() {
        Item item = null;
        if(hasNext()) {
            item = items[currentIndex];
            currentIndex++;
            skipWorthless();
        } else {
            throw new NoSuchElementException("There are no more valuable items");
        }
        return item;
    }


}
