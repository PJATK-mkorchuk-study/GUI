package task05.container;

public class Box<T> {
    private T content;
    public Box() {
        content = null;
    }

    public Box(T content) {
        this.content = content;
    }

    public void put(T item) {
        if(content != null) {
            throw new IllegalStateException("container must be emptied before it can be reused");
        } else {
            content = item;
        }
    }

    public T get() {
        if(content == null) {
            throw new IllegalStateException("container is empty");
        } else {
            return content;
        }
    }

    public T getAndClear() {
        T item = get();
        clear();
        return item;
    }

    public boolean isEmpty() {
        return content == null;
    }

    public void clear() {
        content = null;
    }

    @Override
    public String toString() {
        if(isEmpty()) {
            return "EMPTY";
        } else {
            return "Box{" + content.toString() + "}";
        }

    }
}
