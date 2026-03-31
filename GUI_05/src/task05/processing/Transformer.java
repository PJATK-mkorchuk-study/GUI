package task05.processing;

@FunctionalInterface
public interface Transformer<T, R> {
    public R transform(T input);

}
