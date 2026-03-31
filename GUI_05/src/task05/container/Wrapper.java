package task05.container;

import task05.processing.Transformer;

public class Wrapper<T> {
    private final T content;
    private final transient String sealId;

    public Wrapper(T content, String sealId) {
        this.content = content;
        this.sealId = sealId;
    }

    public T unwrap() {
        System.out.println("[SECURITY] Seal " + sealId + " broken");
        return content;
    }


    public <R> Wrapper<R> map(Transformer<T, R> transformer) {
        R transformed = transformer.transform(this.content);
        return new Wrapper<R>(transformed, sealId + "-MOD");
    }

    public String getSealId() {
        return sealId;
    }

    @Override
    public String toString() {
        return "Wrapper{seal='" + sealId + "', content=" + content + "}";
    }
}
