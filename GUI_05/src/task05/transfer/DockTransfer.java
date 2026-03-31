package task05.transfer;

import task05.container.Box;

public class DockTransfer {
    public static <T> void transfer(Box<? extends T> source,
                                    Box<? super T> destination) {
        if(source.isEmpty()) {
            throw new IllegalStateException("There is no element to transfer");
        }

        T item = source.getAndClear();
        destination.put(item);
        System.out.println("[DOCK] Transferred: " + item.toString());
    }

    public static <T> void copy(Box<? extends T> source, Box<?
            super T> destination) {
        if(source.isEmpty()) {
            throw new IllegalStateException("there is no element to copy");
        }

        T item = source.get();
        destination.put(item);
        System.out.println("the element has been copied");
    }


}
