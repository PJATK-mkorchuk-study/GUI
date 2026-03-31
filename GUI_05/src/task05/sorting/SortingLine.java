package task05.sorting;

public class SortingLine {

    public static <T> void sort(T[] items, FieldComparator<T> comparator) {
        for(int i = 0; i <= items.length - 2; i++) {
            for(int j = 0; j <= items.length - 2 - i; j++) {
                if(comparator.compare(items[j], items[j + 1]) > 0) {
                    T tmp = items[j];
                    items[j] = items[j + 1];
                    items[j + 1] = tmp;
                }
            }
        }
    }

    public static <T> void printLine(String label, T[] items) {
        System.out.println("---" + label + "---");
        for (T item : items) {
            System.out.println(" " + item.toString());
        }
    }
}
